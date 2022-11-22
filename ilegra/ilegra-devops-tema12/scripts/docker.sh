#!/bin/bash

run() {
    docker run -d -p 8088:8080 --name gontainer-74 go-calc-3891:1.0
}

stop() {
    docker stop gontainer-74
}

clear() {
    docker rm gontainer-74
    docker image rm go-calc-3891:1.0
}

status() {
  if [ "$( docker container inspect -f '{{.State.Running}}' gontainer-74 )" == "true" ]; then
          echo "Status: RUNNING"
      else
          echo "Status: STOPPED"
      fi
}

"$@"