FROM amazoncorretto:21
RUN yum -y update && yum clean all && rm -rf /var/cache/yum
COPY build/libs/black-market-data-0.0.1-SNAPSHOT.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/application.jar"]
