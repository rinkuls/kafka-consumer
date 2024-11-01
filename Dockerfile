#################
# Build the  JAVA APP #


#################
#################  from here working
FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD target/kafka-consumer-0.0.1-SNAPSHOT.jar kafka-consumer.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /kafka-consumer.jar" ]