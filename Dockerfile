FROM anapsix/alpine-java
COPY billingcontract-0.0.1-SNAPSHOT.jar /home/billingcontract-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/home/billingcontract-0.0.1-SNAPSHOT.jar"]