docker rm $(docker ps -aq)
docker rmi -f $(docker images mvcpcbmaker* -q)
rm -rf ../MvcPcbMaker/Application/frontend/build
rm -rf ../MvcPcbMaker/Application/frontend/node_modules
mvn clean -f ../MvcPcbMaker/Application/pom.xml
