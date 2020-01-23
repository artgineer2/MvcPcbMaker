mvn clean install -f ../MvcPcbMaker/Application/pom.xml
docker-compose -f ../MvcPcbMaker/docker-compose.yml build
