FROM anapsix/alpine-java
COPY billingcontract-0.0.1.jar /home/billingcontract-0.0.1.jar
CMD ["java", "-jar", "/home/billingcontract-0.0.1.jar"]