#!/bin/bash

DB_DUMP_LOCATION="/tmp/psql_data/db.sql"

echo "*** CREATING DATABASE ***"

psql -U postgres -h localhost < "$DB_DUMP_LOCATION";

echo "*** DATABASE CREATED! ***"