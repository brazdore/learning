#!/bin/bash

start() {
    docker build -t go-calc-2943 .
    docker run -d -p 8080:8080 --name gontainer-04 go-calc-2943
}

stop() {
    docker stop gontainer-04
}

clear() {
    docker rm gontainer-04
    docker image rm go-calc-2943
}

status() {
  if [ "$( docker container inspect -f '{{.State.Running}}' gontainer-04 )" == "true" ]; then
          echo "Status: RUNNING"
      else
          echo "Status: STOPPED"
      fi
}

if [ $1 = "start" ]; then start 
fi

if [ $1 = "stop" ]; then stop 
fi

if [ $1 = "clear" ]; then clear 
fi

if [ $1 = "status" ]; then status 
fi
