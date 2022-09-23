#!/bin/bash

function start() {
	echo ${PATH}
	echo "start mkdocs"
	/Library/Frameworks/Python.framework/Versions/3.10/bin/mkdocs serve &
  # /Library/Frameworks/Python.framework/Versions/3.10/bin/mkdocs serve --dev-addr 127.0.0.1:8080 
	 
}

function stop() {
	echo "stop mkdocs client..."
  ps -ef | grep "mkdocs" | awk  '{ print $2 }' | xargs  kill 
}

case ${1} in
  "start")
	start
  ;;
  "stop")
 	stop	
  ;;
  "restart")
	stop
	sleep 3
	start
  ;;
  "")
	start	
  ;;

  *)
  echo "error arguents"
  exit 1
  ;;
esac





