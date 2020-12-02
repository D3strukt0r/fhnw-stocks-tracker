const DEBUG = false;
const API_URL = "http://localhost:8080/api";
const BASE_URL = "http://localhost:8080";
const ACCESS_TOKEN = 'accessToken';

/**
 * API Request functionality
 */
/**
 * @param {Object} options Options to pass to fetch()
 * @returns {Object} All the headers inside an object
 */
const createHeadersFromOptions = (options) => {
    const requestHeaders = (options.headers || { Accept: 'application/json' });
    // if (
    //     !requestHeaders.hasOwnProperty('Content-Type') &&
    //     !(options && (!options.method || options.method === 'GET')) &&
    //     !(options && options.body)
    // ) {
    //     requestHeaders.set('Content-Type', 'application/json');
    // }
    if (options && options.method && options.method !== 'GET') {
        options.headers.set('X-XSRF-TOKEN', getCookie("XSRF-TOKEN"));
    }
    requestHeaders.set('Content-Type', 'application/json');
    if (options.user && options.user.authenticated && options.user.token) {
        requestHeaders.set('Authorization', options.user.token);
    }

    return requestHeaders;
};

// fetchJson('http://localhost:8080/api/user').then(response => console.log(response.json))

/**
 * @param {String} url The url to fetch information from
 * @param {Object} options Options to pass to fetch()
 * @returns {Promise} The HTTP request
 */
const fetchJson = (url, options = {}) => {
    const requestHeaders = createHeadersFromOptions(options);

    return fetch(url, { ...options, headers: requestHeaders })
        .then(response =>
            response.text().then(text => ({
                status: response.status,
                statusText: response.statusText,
                headers: response.headers,
                body: text,
            }))
        )
        .then(({ status, statusText, headers, body }) => {
            let json;
            try {
                json = JSON.parse(body);
            } catch (e) {
                // not json, no big deal
            }
            if (status < 200 || status >= 300) {
                return Promise.reject(
                    new Error(
                        (json && json.message) || statusText,
                        status,
                        json
                    )
                );
            }
            return Promise.resolve({ status, headers, body, json });
        });
};

/**
 * @param {String} url The url to fetch information from
 * @param {Object} options Options to pass to fetch()
 * @returns {Promise} The HTTP request
 */
const httpClient = (url, options = {}) => {
    options.headers = new Headers();
    options.headers.set('Accept', 'application/json');
    if (localStorage.getItem(ACCESS_TOKEN)) {
        // add your own headers here
        options.headers.set('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN));
    }
    return fetchJson(url, options);
};

function getCookie(name) {
    var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    if (match) return match[2];
}

const DataRequestType = {
    GET_LIST: 'GET_LIST',
    GET_ONE: 'GET_ONE',
    UPDATE: 'UPDATE',
    CREATE: 'CREATE',
    DELETE: 'DELETE',
}

/**
 * Maps react-admin queries to a REST API implemented using Java Spring Boot and Swagger
 *
 * @example
 * GET_LIST     => GET http://my.api.url/posts?pageNumber=0&pageSize=10
 * GET_ONE      => GET http://my.api.url/posts/123
 * GET_MANY     => GET http://my.api.url/posts?id=1234&id=5678
 * UPDATE       => PUT http://my.api.url/posts/123
 * CREATE       => POST http://my.api.url/posts
 * DELETE       => DELETE http://my.api.url/posts/123
 */
const dataProvider = (apiUrl, client = fetchJson) => {
    /**
     * @param {String} type One of the constants appearing at the top if this file, e.g. 'UPDATE'
     * @param {String} resource Name of the resource to fetch, e.g. 'posts'
     * @param {Object} params The data request params, depending on the type
     * @returns {Object} { url, options } The HTTP request parameters
     */
    const convertDataRequestToHTTP = (type, resource, params) => {
        let url = "";
        const options = {};
        switch (type) {
            case DataRequestType.GET_MANY_REFERENCE:
            case DataRequestType.GET_LIST: {
                url = `${apiUrl}/${resource}`
                break;
            }
            case DataRequestType.UPDATE:
                url = `${apiUrl}/${resource}/${params.id}`;
                options.method = "PUT";
                options.body = JSON.stringify(params.data);
                break;
            case DataRequestType.CREATE:
                url = `${apiUrl}/${resource}`;
                options.method = "POST";
                options.body = JSON.stringify(params.data);
                break;
            case DataRequestType.DELETE:
                url = `${apiUrl}/${resource}/${params.id}`;
                options.method = "DELETE";
                break;
            default:
                throw new Error(`Unsupported fetch action type ${type}`);
        }
        return {url, options};
    };

    /**
     * @param {Object} response HTTP response from fetch()
     * @param {String} type One of the constants appearing at the top if this file, e.g. 'UPDATE'
     * @param {String} resource Name of the resource to fetch, e.g. 'posts'
     * @param {Object} params The data request params, depending on the type
     * @returns {Object} Data response
     */
    const convertHTTPResponse = (response, type, resource, params) => {
        const {json} = response;
        switch (type) {
            case DataRequestType.GET_LIST:
            case DataRequestType.CREATE:
                return {data: json};
            case DataRequestType.UPDATE:
            case DataRequestType.GET_ONE:
                return {data: json};
            case DataRequestType.DELETE:
                return {};
            default:
                throw new Error(`Response for this operation (${type}) is not implemented in dataProvider - check it, please.`);
        }
    };

    /**
     * @param {string} type Request type, e.g GET
     * @param {string} resource Resource name, e.g. "users"
     * @param {Object} payload Request parameters. Depends on the request type
     * @returns {Promise} the Promise for a data response
     */
    return (type, resource, params) => {
        const {url, options} = convertDataRequestToHTTP(type, resource, params);
        return client(url, options).then(response =>
            convertHTTPResponse(response, type, resource, params)
        );
    };
};

// Initialize data provider
const apiDataProvider = dataProvider(API_URL, httpClient);
const baseDataProvider = dataProvider(BASE_URL, httpClient);


const register = (form, callbackSuccess, callbackError) => {
    var data = getFormData(form);

    const params = {};
    params.data = data;
    apiDataProvider(DataRequestType.CREATE, "user", params).then(response => {
        return callbackSuccess(response);
    }, error => {
        return callbackError(error);
    })
}

const login = (form, callbackSuccess, callbackError) => {
    var data = getFormData(form);

    const params = {};
    params.data = data;
    baseDataProvider(DataRequestType.CREATE, "login", params).then(response => {
        return callbackSuccess(response);
    }, error => {
        return callbackError(error);
    })
}

/*
const login = ({ username, password }) => {
    if (username === 'login' && password === 'password') {
        localStorage.removeItem('not_authenticated');
        localStorage.removeItem('role');
        localStorage.setItem('login', 'login');
        localStorage.setItem('user', 'John Doe');
        localStorage.setItem(
            'avatar',
            'data:image/jpeg;base64,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'
        );
        return Promise.resolve();
    }
    if (username === 'user' && password === 'password') {
        localStorage.setItem('role', 'user');
        localStorage.removeItem('not_authenticated');
        localStorage.setItem('login', 'user');
        localStorage.setItem('user', 'Jane Doe');
        localStorage.setItem(
            'avatar',
            'data:image/jpeg;base64,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'
        );
        return Promise.resolve();
    }
    if (username === 'admin' && password === 'password') {
        localStorage.setItem('role', 'admin');
        localStorage.removeItem('not_authenticated');
        localStorage.setItem('login', 'admin');
        localStorage.setItem('user', 'Dennis Nedry');
        localStorage.setItem(
            'avatar',
            'data:image/jpeg;base64,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'
        );
        return Promise.resolve();
    }
    localStorage.setItem('not_authenticated', true);
    return Promise.reject();
};
*/
const logout = () => {
    localStorage.setItem('not_authenticated', true);
    localStorage.removeItem('role');
    localStorage.removeItem('login');
    localStorage.removeItem('user');
    localStorage.removeItem('avatar');
    return Promise.resolve();
};

const checkLoginError = ({ status }) => {
    return status === 401 || status === 403
        ? Promise.reject()
        : Promise.resolve();
};

const checkAuth = () => {
    return localStorage.getItem('not_authenticated')
        ? Promise.reject()
        : Promise.resolve();
};

const getPermissions = () => {
    const role = localStorage.getItem('role');
    return Promise.resolve(role);
};

const getIdentity = () => {
    return {
        id: localStorage.getItem('login'),
        fullName: localStorage.getItem('user'),
        avatar: localStorage.getItem('avatar'),
    };
};

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}
