How to run

There are 4 micro services which are catalog, deployment, approval and provision services

Each service talks to other services to handle the user request

There is script.sh in docker-request-job folder which contains the sample commands to trigger the request

deploy-commands.txt file contains deployment to k8s cluster

You need postgres database to run this micro services

DB:

1) docker run -d --rm -p 5432:5432 --name postgres -e POSTGRES_PASSWORD=password -e POSTGRES_USER=postgres -e
   POSTGRES_DB=postgres postgres -c 'max_connections=110'

2) docker exec -it postgres /bin/bash

3) psql -U postgres -h localhost

4) DROP DATABASE IF EXISTS catalog_db;
   create database catalog_db;
   grant all privileges on database catalog_db to postgres;
   alter database catalog_db owner to postgres;

DROP DATABASE IF EXISTS deployment_db;
create database deployment_db;
grant all privileges on database deployment_db to postgres;
alter database deployment_db owner to postgres;

DROP DATABASE IF EXISTS approval_db;
create database approval_db;
grant all privileges on database approval_db to postgres;
alter database approval_db owner to postgres;

DROP DATABASE IF EXISTS provision_db;
create database provision_db;
grant all privileges on database provision_db to postgres;
alter database provision_db owner to postgres;

Run Services locally:

>java -jar $JAR_NAME -Dspring.profiles.active=$PROFILE

Catalog Service: -Dspring.profiles.active=catalog

Approval Service: -Dspring.profiles.active=approval

Deployment Service: -Dspring.profiles.active=deployment

Provision Service: -Dspring.profiles.active=provision

If you want make any changes and deploy it k8s then run mvn clean install -DskipTests=true and this will generate the
jar in docker/files folder
and after that you do docker build -t <image-tag> . and then point this image in ba-deployment.yaml file
