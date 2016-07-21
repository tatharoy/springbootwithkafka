FROM tedder42/java7-jre
MAINTAINER tathagata.roy@citiustech.com
EXPOSE 8080
CMD java -jar springbootdemo-0.0.1-SNAPSHOT.jar
ADD target/springbootdemo-0.0.1-SNAPSHOT.jar .