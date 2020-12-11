# The different stages of this Dockerfile are meant to be built into separate images
# https://docs.docker.com/develop/develop-images/multistage-build/#stop-at-a-specific-build-stage
# https://docs.docker.com/compose/compose-file/#target

# -------
# Builder
# -------
FROM --platform=${BUILDPLATFORM:-linux/amd64} gradle:jdk8 AS build

WORKDIR /home/gradle/src

COPY --chown=gradle:gradle . .

RUN set -eux; \
    \
    gradle build --no-daemon; \
    mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

# ---------------
# Final Container
# ---------------
FROM openjdk:8-jdk-alpine

ARG DEPENDENCY=/home/gradle/src/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib     /app/lib
COPY --from=build ${DEPENDENCY}/META-INF         /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

CMD ["java", "-cp", "app:app/lib/*", "fhnw.dreamteam.stockstracker.StocksTrackerApplication"]
