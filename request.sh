#! /bin/bash

curl 'http://localhost:8080/channels?startInclusive=2016-06-25&endExclusive=2016-06-27&nofOccurrences=4
&durationSeconds=30' \
     --user user:user-password \
     --basic \
     --verbose
