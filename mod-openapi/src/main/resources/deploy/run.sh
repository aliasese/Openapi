#!/usr/bin/env bash

OKAPI=${1:-"http://localhost:9130"} # The usual place it runs on a single-machine setup
SLEEP=${2:-"1"} # Time to sleep between requests
CURLOPTS="-w\n -D - " # -w to output a newline after, -D - to show headers

echo
echo "Check that Okapi is running ..."
curl $CURLOPTS $OKAPI/_/discovery/nodes || exit 1
echo "OK"
sleep $SLEEP

echo
echo "Declaring the module"

curl $CURLOPTS -X POST  \
  -H "Content-type: application/json" \
  -d @ModuleDescriptor.json \
  $OKAPI/_/proxy/modules || exit 1
echo "OK"
sleep $SLEEP

echo
echo "Deploying it on localhost"
curl $CURLOPTS -X POST  \
  -H "Content-type: application/json" \
  -d @DeploymentDescriptor.json  \
  $OKAPI/_/discovery/modules || exit 1
echo "OK"
sleep $SLEEP


echo
echo "Enabling it for our tenant"
curl $CURLOPTS -X POST \
  -H "Content-type: application/json" \
  -d @TenantModuleDescriptor.json \
  $OKAPI/_/proxy/tenants/testlib/modules || exit 1
echo "OK"
