#!/bin/sh
echo Deploying version $VERSION ...
wget -t 1 -O /dev/null http://localhost:9099
echo Waiting for old version to stop ...
sleep 7
rm cdzero2hero-app-*.jar
wget http://10.0.2.2:8081/artifactory/libs-release/com/axelfontaine/cdzero2hero-app/$VERSION/cdzero2hero-app-$VERSION.jar
nohup java -jar cdzero2hero-app-$VERSION.jar 0<&- &>/dev/null &
echo Waiting for new version to start ...
sleep 5
res=$( curl -w %{http_code} -s --output /dev/null http://localhost:9100)
if [ $res -ne 200 ]
    then
        echo Error: instance came up with $res
        exit 1
    fi
echo Done.
