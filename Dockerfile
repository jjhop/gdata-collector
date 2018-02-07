FROM dockerfile/java:oracle-java8
MAINTAINER r.kotusiewicz@gmail.com
RUN apt-get update

COPY build/libs/*.jar /opt/

CMD ["java", "-jar", "/opt/gdata-collector-0.1.jar"]
