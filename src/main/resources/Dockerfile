FROM tomcat:8.5.47-jdk8

MAINTAINER chris

ARG WARFILE

ADD build/libs/bob.war /usr/local/tomcat/webapps/bob.war
#ADD $WARFILE /usr/local/tomcat/webapps/bob.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
