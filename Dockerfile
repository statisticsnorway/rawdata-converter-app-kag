FROM adoptopenjdk/openjdk14:alpine
RUN apk --no-cache add curl
COPY target/rawdata-converter-app-kag-*.jar rawdata-converter-app-kag.jar
COPY target/classes/logback*.xml /conf/
EXPOSE 8080
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-Dcom.sun.management.jmxremote", "--enable-preview", "-Xmx1g", "-jar", "rawdata-converter-app-kag.jar"]
