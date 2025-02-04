#!/bin/bash

mvn clean install -DskipTests=true -Dspring.profiles.active=approval
mv docker/files/hub-app-management-spring-demo-apps-0.0.1-SNAPSHOT.jar docker/files/hub-app-management-spring-demo-apps-0.0.1-SNAPSHOT-approval.jar

mvn install -DskipTests=true -Dspring.profiles.active=catalog
mv docker/files/hub-app-management-spring-demo-apps-0.0.1-SNAPSHOT.jar docker/files/hub-app-management-spring-demo-apps-0.0.1-SNAPSHOT-catalog.jar

mvn install -DskipTests=true -Dspring.profiles.active=deployment
mv docker/files/hub-app-management-spring-demo-apps-0.0.1-SNAPSHOT.jar docker/files/hub-app-management-spring-demo-apps-0.0.1-SNAPSHOT-deployment.jar

mvn install -DskipTests=true -Dspring.profiles.active=provision
mv docker/files/hub-app-management-spring-demo-apps-0.0.1-SNAPSHOT.jar docker/files/hub-app-management-spring-demo-apps-0.0.1-SNAPSHOT-provision.jar