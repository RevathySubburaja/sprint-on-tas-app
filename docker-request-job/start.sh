#!/bin/bash

while true
do
  echo "Success scenario"

  for i in {1..10}
  do

    curl --location --request GET 'http://catalog-service:8081/api/catalog'

    curl --location --request GET 'http://deployment-service:8082/api/deployment'

    curl --location --request GET 'http://approval-service:8083/api/approval'
  done

  catalog_id=$(curl -s --location --request GET 'http://catalog-service:8081/api/catalog' | jq -r '.[0].id')

  catalog_url="http://catalog-service:8081/api/catalog/$catalog_id/request"

  for i in {1..50}
  do
    echo "creating deployment"
    curl --location --request POST "$catalog_url" --header 'Content-Type: application/json' --data-raw '{"name": "jira-postgres-database","description": "database for jira application","inputs": {"cpu" : 16, "memory": "32GB","storage": "1TB","type": "SSD" }}'

    echo "creating deployment completed"
  done

  echo "Error scenario"

  for i in {1..25}
  do

    curl --location --request GET 'http://catalog-service:8081/api/catalog/serviceCalls'

    curl --location --request GET 'http://deployment-service:8082/api/deployment/serviceCalls'

    curl --location --request GET 'http://approval-service:8083/api/approval/serviceCalls'

    curl --location --request GET 'http://provision-service:8084/api/provision/serviceCalls'
  done

  echo "Error scenario done"

  echo "sleeping......"

  sleep 1m

done