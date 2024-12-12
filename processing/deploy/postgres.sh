#!/bin/bash
docker run --name postgres -p 5432:5432 \
-e POSTGRES_DB=processing -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password \
-v ~/container-volumes/db/postgres:/var/lib/postgresql/data:rw \
-d postgres:17.0