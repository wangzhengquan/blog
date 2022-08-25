#!/bin/bash

function start() {
	echo ${PATH}
	echo "start mkdocs"
	/Library/Frameworks/Python.framework/Versions/3.10/bin/mkdocs serve &
	 
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





