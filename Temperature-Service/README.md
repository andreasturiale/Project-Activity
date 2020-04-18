# Temperature-Service

## Description
This microservice consists of two main modules: the first one manages the Mqtt messages sent by the sensor through the RabbitMQ broker and notifies users via email on the basis of the current message; the second one allows users to add the email or change the temperature threshold beyond which they want to be warned by saving this information in a special external database. Both modules are structured on three levels in order to separate the level that answers external calls (in green), to the one that implements the microservice business logic (in purple) interacting with an external service through the support of Spring Boot which respectively provides the Repositories for the databases and MailSender to send an email.

![Architettura](/Doc/images/Temperature-Service.png) </br>

## Configuration
**Note:** before the execution you have to configure the **application.yml** and **hazelcast.xml** in order to connect with Mysql database, RabbitMQ, Gmail and Hazelcast. </br>

---

To see the full italian documentation go to */Doc/Documentazione.pdf*.