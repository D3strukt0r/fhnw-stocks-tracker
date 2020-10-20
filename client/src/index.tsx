import React from "react";
import ReactDOM from "react-dom";
import { Route } from 'react-router-dom';
import { Admin, Resource } from 'react-admin';

import authProvider from './authProvider';
import CustomRouteLayout from './customRouteLayout';
import CustomRouteNoLayout from './customRouteNoLayout';
import dataProvider from './dataProvider';
import i18nProvider from './i18nProvider';
import Layout from './Layout';

import comments from './comments';
import posts from './posts';
import users from './users';
import tags from './tags';

import * as serviceWorker from "./serviceWorker";

ReactDOM.render(
    <React.StrictMode>
        <Admin
            authProvider={authProvider}
            dataProvider={dataProvider}
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
                permissions ? <Resource name="users" {...users} /> : null,
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
