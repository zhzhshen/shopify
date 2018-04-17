#!/bin/sh

mvn clean package
docker-compose up --build -d