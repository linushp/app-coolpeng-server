#!/bin/sh
echo "invoke me at comp-proto directory"

if [ "$#" -lt 1 ]; then
  echo "Usage: $0 [proto file] "
  exit 1
fi

protoc --java_out=src/main/java/  --proto_path=./ $1
echo "ok"
#if [[ $1 =~ .*app/.* ]]; then
#echo "gen app proto"
#fn=`basename $1`
#cd app
#protoc --java_out=../src/main/java/  --proto_path=./ $fn
#echo "finished"
#exit 0
#fi
#
#echo "gen service proto"
#mv $1 app/
#cd app
#protoc --java_out=../src/main/java/  --proto_path=./ $1
#mv $1 ..
#echo "finished"
