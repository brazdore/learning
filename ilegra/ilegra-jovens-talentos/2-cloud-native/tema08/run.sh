# shellcheck disable=SC2086
if [ $1 = "start" ]; then
  # Docker: Image Build
  sudo docker build -t rxnetty-calc-8659 .
  # Docker: Container Run
  sudo docker run -d -p 8080:8080 rxnetty-calc-8659
fi

if [ "$1" = "stop" ]; then
  # shellcheck disable=SC2046
  sudo docker kill $(docker ps -a -q)
fi