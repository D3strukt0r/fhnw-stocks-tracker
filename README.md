# fhnw-stocks-tracker

Tracks stocks owned on different platforms

Project

[![License](https://img.shields.io/github/license/FHNW-Dream-Team/stocks-tracker)][license]
[![Docker Stars](https://img.shields.io/docker/stars/d3strukt0r/fhnw-stocks-tracker-api.svg?label=docker%20stars%20(api))][docker-api]
[![Docker Pulls](https://img.shields.io/docker/pulls/d3strukt0r/fhnw-stocks-tracker-api.svg?label=docker%20pulls%20(api))][docker-api]
[![Docker Stars](https://img.shields.io/docker/stars/d3strukt0r/fhnw-stocks-tracker-client.svg?label=docker%20stars%20(client))][docker-client]
[![Docker Pulls](https://img.shields.io/docker/pulls/d3strukt0r/fhnw-stocks-tracker-client.svg?label=docker%20pulls%20(client))][docker-client]

master-branch (alias stable, latest)

[![GH Action CI/CD](https://github.com/FHNW-Dream-Team/stocks-tracker/workflows/CI/CD/badge.svg?branch=master)][gh-action]
[![Codacy grade](https://img.shields.io/codacy/grade/6695eaec0c6047abae98cbda78f9873f/master)][codacy]

develop-branch (alias nightly)

[![GH Action CI/CD](https://github.com/FHNW-Dream-Team/stocks-tracker/workflows/CI/CD/badge.svg?branch=develop)][gh-action]
[![Codacy grade](https://img.shields.io/codacy/grade/0e799065d4724438828fc852bf0336d4/develop)][codacy]

## Getting Started (Development)

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

```shell
git clone https://github.com/FHNW-Dream-Team/stocks-tracker.git
```

Please further read on the [wiki](https://github.com/FHNW-Dream-Team/stocks-tracker/wiki/Development-environment-setup).

## Getting Started (Production)

In order to run this container you'll need docker installed.

-   [Windows](https://docs.docker.com/docker-for-windows/install/)
-   [OS X](https://docs.docker.com/docker-for-mac/install/)
-   [Linux](https://docs.docker.com/install/linux/docker-ce/ubuntu/)

### Usage of Docker images

#### Container Parameters

```shell
docker run \
    -p 8080:8080
    -e <all the keys>
    d3strukt0r/fhnw-stocks-tracker-client
```

```shell
docker run \
    -p 80:80
    d3strukt0r/fhnw-stocks-tracker-api
```

For a full example with `Docker Compose` please check the [wiki](https://github.com/FHNW-Dream-Team/stocks-tracker/wiki/Production-setup).

#### Environment Variables

All environment variables support Docker Secrets. To learn more about Docker Secrets, read [here](https://docs.docker.com/engine/swarm/secrets/).

Basically, after creating a secret, append a `_FILE` (e. g. `DB_PASSWORD_FILE`) after the environment variable and set the path to something like `/run/secrets/<something>`.

##### API Envs

-   `SPRING_APPLICATION_JSON` - Configuration to pass to spring boot (JSON)

##### Client Envs

-   `NGINX_CLIENT_MAX_BODY_SIZE` - The maximum size for sending POST requests (maximum upload size) (has to be the same on php) (Default: `100M`)
-   `USE_HTTPS` - Enables https. (Not recommeded, rather use Traefik) (Default: `false`)

#### Volumes

-   `/app` - Where the app is stored

## Built With

-   [JavaScript](https://www.ecma-international.org/publications/standards/Ecma-262.htm) - Programming language
-   [Bootstrap](https://getbootstrap.com/) - Frontend framework
-   [SB Admin 2](https://startbootstrap.com/theme/sb-admin-2) - Theme for client
-   [Java](https://docs.oracle.com/en/java/) - Programming language
-   [Spring Boot](https://spring.io/projects/spring-boot) - Java web framework
-   [Github Actions](https://github.com/features/actions) - Automatic CI (Testing) / CD (Deployment)
-   [Docker](https://www.docker.com/) - Containerized image

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/FHNW-Dream-Team/stocks-tracker/tags).

## Authors

-   **Manuele Vaccari** - [D3strukt0r](https://github.com/D3strukt0r) - _Initial work, Setup client, Create stocks, Edit stocks_
-   **Victor Hargrave** - [jokerengine](https://github.com/jokerengine) - _Setup backend, Register, Detail stock_
-   **Sasa Trajkova** - [sasatrajkova](https://github.com/sasatrajkova) - _Stocks overview, Edit profile, Delete stock_
-   **Thomas Weber** - [tjw52](https://github.com/tjw52) - _Login, Logout, CRUD Currency_

See also the list of [contributors](https://github.com/FHNW-Dream-Team/stocks-tracker/contributors) who participated in this project.

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.txt](LICENSE.txt) file for details.

## Acknowledgments

-   Hat tip to anyone whose code was used
-   Inspiration
-   etc

[license]: https://github.com/FHNW-Dream-Team/stocks-tracker/blob/master/LICENSE.txt
[docker-api]: https://hub.docker.com/repository/docker/d3strukt0r/fhnw-stocks-tracker-api
[docker-client]: https://hub.docker.com/repository/docker/d3strukt0r/fhnw-stocks-tracker-client
[gh-action]: https://github.com/FHNW-Dream-Team/stocks-tracker/actions
[codacy]: https://app.codacy.com/gh/FHNW-Dream-Team/stocks-tracker
