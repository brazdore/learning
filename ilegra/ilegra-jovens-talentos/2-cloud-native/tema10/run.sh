  if [ $1 = "start" ]; then
  
  # Docker: Image Build
  sudo docker build -t twitter-3702 twitter
  sudo docker build -t github-3702 github
  sudo docker build -t sum-3702 sum
  
  # Docker: Container Run
  sudo docker run -d -p 8081:8081 twitter-3702
  sudo docker run -d -p 8082:8082 github-3702
  sudo docker run -d -p 8083:8083 --network="host" sum-3702
  fi
  
  if [ $1 = "stop" ]; then
  # shellcheck disable=SC2046
  sudo docker stop $(docker ps -a -q)
  fi