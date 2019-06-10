#!/usr/bin/env bash

git submodule update --init --recursive

docker build -t wnameless/docker-oracle-xe-11g docker-images/wnameless/docker-oracle-xe-11g

mvn clean install