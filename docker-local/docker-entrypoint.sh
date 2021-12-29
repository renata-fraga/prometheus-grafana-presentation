#!/bin/sh
set -e
exec java $@ -jar /app/prometheus-grafana-presentation.jar -Dfile.encoding=UTF-8