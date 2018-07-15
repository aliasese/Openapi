#!/usr/bin/env bash
OKAPI=${1:-"http://localhost:9130"} # The usual place it runs on a single-machine setup
SLEEP=${2:-"1"} # Time to sleep between requests
CURLOPTS="-w\n -D - " # -w to output a newline after, -D - to show headers
echo
echo "Creating a tenant"
curl $CURLOPTS -X POST  \
  -H "Content-type: application/json" \
  -d @TenantDescriptor.json  \
  $OKAPI/_/proxy/tenants || exit 1
echo OK
sleep $SLEEP