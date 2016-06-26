#! /bin/bash

file=sample-service-providers-0.1.0.jar
curl 'http://localhost:8080/channel-pricing-jars' \
     --verbose \
     --basic \
     --user admin:admin-password \
     --form "file=@sample-service-providers/target/$file;filename=$file"
