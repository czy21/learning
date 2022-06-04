#!/bin/sh

modules=("infrastructure" "spring-bom" "spring-db" "spring-web" "spring-pulsar")

tasks=("clean" "publishAllPublicationsToHomeSnapshotsRepository")

modules_cmd=""
for p in ${modules[@]}; do
  for t in ${tasks[@]}; do
    modules_cmd+="${p}:${t} "
  done
done
echo ${modules_cmd}
./gradlew ${modules_cmd} -x test