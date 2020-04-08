# Api-Gateway

## Description
Since the internal architecture is composed only of the TemperatureService, only one route has been inserted in the **application.yml**. It allows to forward any incoming request that will match with *"http://apiGatewayAddress:apiGatewayPort/temperature/users/**"* to an instance of the corresponding microservice.</br>
To avoid fixing the IP address of the microservice directly in the application.yml, a logical name is specified that is used for its resolution by querying the Eureka server transparently when a request arrives. This is done with the appropriate *spring.cloud.gateway.routes.uri: lb://TEMPERATURE-SERVICE*.</br>
Finally, a special filter corresponding to a CircuitBreaker has been added to manage the calls to Temperature-Service using the Resilience4j library. For fallback method a distributed cache is checked to see if there is the searched information.</br>
To see the full italian documentation go to */Doc/Documentazione.pdf*.