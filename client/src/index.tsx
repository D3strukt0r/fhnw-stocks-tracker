import React from "react";
import ReactDOM from "react-dom";
import { Route } from 'react-router-dom';
import { Admin, Resource, fetchUtils } from 'react-admin';

import authProvider from './authProvider';
import CustomRouteLayout from './customRouteLayout';
import CustomRouteNoLayout from './customRouteNoLayout';
import springRestProvider from './dataProvider';
import i18nProvider from './i18nProvider';
import Layout from './Layout';

import {ACCESS_TOKEN, API_PROXY_URL} from "./constants";

import comments from './comments';
import posts from './posts';
import users from './user';
import tags from './tags';

import * as serviceWorker from "./serviceWorker";

const httpClient = (url: string, options = {}) => {
    if (localStorage.getItem(ACCESS_TOKEN)) {
        if (!options.headers) {
            options.headers = new Headers({Accept: 'application/json'});
        }
        // add your own headers here
        options.headers.set('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN));
    }
    return fetchUtils.fetchJson(url, options);
}

const springDataProvider = springRestProvider(API_PROXY_URL, httpClient);

ReactDOM.render(
    <React.StrictMode>
        <Admin
            authProvider={authProvider}
            dataProvider={springDataProvider}
            i18nProvider={i18nProvider}
            title="Example Admin"
            layout={Layout}
            customRoutes={[
                <Route
                    exact
                    path="/custom"
                    component={(props) => <CustomRouteNoLayout {...props} />}
                    noLayout
                />,
                <Route
                    exact
                    path="/custom2"
                    component={(props) => <CustomRouteLayout {...props} />}
                />,
            ]}
        >
            {(permissions) => [
                <Resource name="posts" {...posts} />,
                <Resource name="comments" {...comments} />,
                <Resource name="user" {...users} />,
                <Resource name="tags" {...tags} />,
            ]}
        </Admin>
    </React.StrictMode>,
    document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
