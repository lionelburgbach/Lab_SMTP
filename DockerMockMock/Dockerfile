FROM java:8
MAINTAINER Lionel Burgbacher <lionel.burgbacher@heig-vd.ch>

ADD src /opt/src/

WORKDIR /opt/src/

ENTRYPOINT ["java","-jar","MockMock.jar","-p","25000","-h","8080"]
