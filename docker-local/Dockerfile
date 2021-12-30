#
# RELEASE stage
#
FROM openjdk:17.0.1-slim AS release
COPY /build/libs/ app/
COPY /docker-local/docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]