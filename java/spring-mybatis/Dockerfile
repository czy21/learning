FROM openjdk:11.0.10-jre
WORKDIR /home/app
VOLUME ["/home/app/log","/home/app/config/"]

COPY ./build/libs/portal-0.0.1-RELEASE.jar /home/app/api.jar

#RUN wget https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.15.0/jmx_prometheus_javaagent-0.15.0.jar

RUN ls -al /home/app/

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
EXPOSE 8080
ENTRYPOINT ["java",\
"-jar",\
"/home/app/api.jar",\
"--spring.config.location=file:/home/app/config/"]