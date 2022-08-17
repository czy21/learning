#!/bin/sh

modules=("infrastructure" "spring-bom" "spring-db" "spring-web" "spring-pulsar")

tasks=("clean" "build")

modules_cmd=""
for p in ${modules[@]}; do
  for t in ${tasks[@]}; do
    modules_cmd+="${p}:${t} "
  done
done
./gradlew ${modules_cmd} -x test