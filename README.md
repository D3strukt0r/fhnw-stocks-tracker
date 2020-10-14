# fhnw-stocks-tracker

Tracks stocks owned on different platforms

Project

[![License](https://img.shields.io/github/license/D3strukt0r/fhnw-stocks-tracker)][license]
<!-- [![Docker Stars](https://img.shields.io/docker/stars/d3strukt0r/fhnw-stocks-tracker-api-php.svg?label=docker%20stars%20(php))][docker-php]
[![Docker Pulls](https://img.shields.io/docker/pulls/d3strukt0r/fhnw-stocks-tracker-api-php.svg?label=docker%20pulls%20(php))][docker-php]
[![Docker Stars](https://img.shields.io/docker/stars/d3strukt0r/fhnw-stocks-tracker-api-nginx.svg?label=docker%20stars%20(nginx))][docker-nginx]
[![Docker Pulls](https://img.shields.io/docker/pulls/d3strukt0r/fhnw-stocks-tracker-api-nginx.svg?label=docker%20pulls%20(nginx))][docker-nginx] -->
[![Docker Stars](https://img.shields.io/docker/stars/d3strukt0r/fhnw-stocks-tracker-client.svg?label=docker%20stars%20(client))][docker-client]
[![Docker Pulls](https://img.shields.io/docker/pulls/d3strukt0r/fhnw-stocks-tracker-client.svg?label=docker%20pulls%20(client))][docker-client]

master-branch (alias stable, latest)

[![GH Action CI/CD](https://github.com/D3strukt0r/fhnw-stocks-tracker/workflows/CI/CD/badge.svg?branch=master)][gh-action]
<!-- [![Codacy grade](https://img.shields.io/codacy/grade/6695eaec0c6047abae98cbda78f9873f/master)][codacy] -->

<!--
develop-branch (alias nightly)

[![GH Action CI/CD](https://github.com/D3strukt0r/fhnw-stocks-tracker/workflows/CI/CD/badge.svg?branch=develop)][gh-action]
[![Codacy grade](https://img.shields.io/codacy/grade/6695eaec0c6047abae98cbda78f9873f/develop)][codacy]
-->

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Usage of Docker images

#### Environment Variables

##### Client Envs

-   `NGINX_CLIENT_MAX_BODY_SIZE` - The maximum size for sending POST requests (maximum upload size) (has to be the same on php) (Default: `100M`)
-   `USE_HTTPS` - Enables https. (Not recommeded, rather use Traefik) (Default: `false`)

#### Volumes

-   `/app` - Where the app is stored

## Built With

-   [TypeScript](https://www.typescriptlang.org/) - Programming language
-   [Yarn](https://yarnpkg.com/) - Dependency management
-   [React.js](https://reactjs.org/) - Web framework
-   [Github Actions](https://github.com/features/actions) - Automatic CI (Testing) / CD (Deployment)
-   [Docker](https://www.docker.com/) - Containerized image

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/D3strukt0r/fhnw-stocks-tracker/tags).

## Authors

-   **Manuele Vaccari** - [D3strukt0r](https://github.com/D3strukt0r) - _Initial work_
-   **Victor Hargrave** - [jokerengine](https://github.com/jokerengine) - _TO DO_
-   **Sasa Trajkova** - [sasatrajkova](https://github.com/sasatrajkova) - _TO DO_
-   **Thomas Weber** - [tjw52](https://github.com/tjw52) - _TO DO_

See also the list of [contributors](https://github.com/D3strukt0r/fhnw-stocks-tracker/contributors) who participated in this project.

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.txt](LICENSE.txt) file for details.

## Acknowledgments

-   Hat tip to anyone whose code was used
-   Inspiration
-   etc

[license]: https://github.com/D3strukt0r/fhnw-stocks-tracker/blob/master/LICENSE.txt
<!-- [docker-php]: https://hub.docker.com/repository/docker/d3strukt0r/fhnw-stocks-tracker-api-php
[docker-nginx]: https://hub.docker.com/repository/docker/d3strukt0r/fhnw-stocks-tracker-api-nginx -->
[docker-client]: https://hub.docker.com/repository/docker/d3strukt0r/fhnw-stocks-tracker-client
[gh-action]: https://github.com/D3strukt0r/fhnw-stocks-tracker/actions
[codacy]: https://www.codacy.com/manual/D3strukt0r/fhnw-stocks-tracker
