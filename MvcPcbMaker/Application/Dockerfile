From bitnami/tomcat:latest
copy ./target/MvcPcbMaker-0.0.1-SNAPSHOT.jar MvcPcbMaker-0.0.1-SNAPSHOT.jar
copy ./wait-for-it.sh /wait-for-it.sh
copy ./start.sh /start.sh
ENTRYPOINT ["/bin/sh","-c","/wait-for-it.sh sch_db_mysql:3306 -t 0 -- /start.sh"]

