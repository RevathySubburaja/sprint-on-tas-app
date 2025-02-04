#!/bin/sh -e

SERVICE_HOME=/opt/service

echo "$(date): Starting JVM with ${JAVA_OPTS}"

status=0
set +e

java ${JAVA_OPTS} -jar $SERVICE_HOME/hub-app-management-spring-demo-apps.jar

status=$?
set -e

echo "JVM exited with status code ${status}"
exit ${status}