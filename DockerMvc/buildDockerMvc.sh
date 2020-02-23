cp   ../CustomFiles/Docker/application.properties   ../MvcPcbMaker/Application/src/main/resources/application.properties
cp   ../CustomFiles/Docker/ProjectDataService.js   ../MvcPcbMaker/Application/frontend/src/service/ProjectDataService.js
cp   ../CustomFiles/Docker/Dockerfile_MySql   	../MvcPcbMaker/Database/Dockerfile
mvn clean install -f ../MvcPcbMaker/Application/pom.xml
docker-compose -f ../MvcPcbMaker/docker-compose.yml build
