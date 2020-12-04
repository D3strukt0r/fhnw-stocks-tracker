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

    return fetch(url, { ...options, credentials: "include", headers: requestHeaders })
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
            // if user is unauthenticated then take back to login and clear storage
            if (status === 401 || status === 403) {
                if (window.location.href.indexOf("login") > -1) {
                    // continue
                } else {
                    localStorage.removeItem("username");
                    window.location.replace("login.html");
                    return;
                }
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

    return fetchJson(url, options);
};

function getCookie(name) {

    var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    if (match) return match[2];
}

const DataRequestType = {
    GET_LIST: 'GET_LIST',
    GET: 'GET',
    UPDATE: 'UPDATE',
    CREATE: 'CREATE',
    DELETE: 'DELETE',
    HEAD: 'HEAD',
}

/**
 * Maps react-admin queries to a REST API implemented using Java Spring Boot and Swagger
 *
 * @example
 * GET_LIST     => GET http://my.api.url/posts?pageNumber=0&pageSize=10
 * GET      => GET http://my.api.url/posts/123
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
            case DataRequestType.GET:
                url = `${apiUrl}/${resource}`;
                options.method = "GET";
                break;
            case DataRequestType.GET_LIST: {
                url = `${apiUrl}/${resource}`
                options.method = "GET";
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
            case DataRequestType.GET:
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
        //localStorage.removeItem('not_authenticated');
        //localStorage.setItem(ACCESS_TOKEN, );
        return callbackSuccess(response);
    }, error => {
        //localStorage.setItem('not_authenticated', true);
        return callbackError(error);
    })
}

const username = (callbackSuccess, callbackError) => {
    apiDataProvider(DataRequestType.GET, "clientUser").then(response => {
        return callbackSuccess(response);
    }, error => {
        return callbackError(error);
    })
}

const logout = (callbackSuccess, callbackError) => {
    baseDataProvider(DataRequestType.GET, "logout").then(response => {
        return callbackSuccess(response);
    }, error => {
        return callbackError(error);
    })
}

const getAllStock = (callbackSuccess, callbackError) => {
    apiDataProvider(DataRequestType.GET_LIST, "stock").then(response => {
        return callbackSuccess(response);
    }, error => {
        return callbackError(error);
    })
}

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}
