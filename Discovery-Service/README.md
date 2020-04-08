# Discovery-Service

## Description
The Eureka Server is a component that maintains the information of all the services that are running within the system, in the specific IP address and port of each.
The integration of Spring Cloud with the Eureka library of Netflix OSS facilitates the implementation of the Discovery Server by limiting it to three simple steps:

1. You need to add *spring-cloud-starter-netflix-eureka-server* dependencies in the project.

2. Then you need to enable the Eureka server through the appropriate annotation *@EnableEurekaServer* directly in the class that runs the application main.

3. Finally, you must specify the application properties in **application.yml** based on the system domain. In this case I have configured the component port, **8761**, and added two other attributes to start Eureka Server in standalone mode.</br>

---

To see the full italian documentation go to */Doc/Documentazione.pdf*.