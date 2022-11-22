  if [ $1 = "start" ]; then
  
  # Docker: Image Build
  sudo docker build -t twitter-3702 twitter
  sudo docker build -t github-3702 github
  sudo docker build -t sum-3702 sum
  
  # Docker: Container Run
  sudo docker run -d -p 8080:8080 twitter-3702
  sudo docker run -d -p 8081:8081 github-3702
  sudo docker run -d -p 8082:8082 --network="host" sum-3702
  fi
  
  if [ $1 = "stop" ]; then
  sudo docker kill $(docker ps -a -q)
  fi