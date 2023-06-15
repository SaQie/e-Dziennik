FROM openjdk:17-oracle
EXPOSE 8080
ADD /web/target/E-Dziennik.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]