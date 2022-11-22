#!/bin/bash

run() {
    docker run -d -p 8080:8080 --name gontainer-12 go-calc-7621:1.0
}

stop() {
    docker stop gontainer-12
}

clear() {
    docker rm gontainer-12
    docker image rm go-calc-7621:1.0
}

status() {
  if [ "$( docker container inspect -f '{{.State.Running}}' gontainer-12 )" == "true" ]; then
          echo "Status: RUNNING"
      else
          echo "Status: STOPPED"
      fi
}

"$@"
