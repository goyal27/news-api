## Sapient News API

### Running Instructions 

* docker build -f Dockerfile -t sapient-newsapi .
* nohup docker run -p 8080:8080 sapient-newsapi  -Dnews.api.apiKey=bcb3196003024d96bdc95ff94094aa44 &
* Ideally API key will be passed at runtime from deployment pipeline after fetched from secure key-vault as prt of CD.

### Endpoint Details 
* Once application is Running using above command, access swagger at  http://localhost:8080/swagger-ui.html#/news-api/getNewsUsingGET
* Using swagger documentation we can try out news API by passing the keyword and other optional parameters.

### Design Patterns and Principles Followed

* Fault tolerance and rate limiting by using resilience-4j library, demonstrating use of `Decorator Pattern`
* Demonstrating `Strategy Pattern` by having an interface `NewsResponseGroupingStrategy`, and injecting it to service class catering to dynamic grouping methods.
* Demonstrating `Builder Pattern` in each of the model objects.
* `Single Responsibility Principle` being followed by every class and interface.
* Interfaces are segregated to client, strategy showing `Interface Segregation`
* Design is open for extension by allowing the injection of different strategies of grouping news items.
* Interaction between service(`NewsService`) and api-client (`NewsClient` interface) is using generic contact, allowing use of different public api in future without disturbing the api and service class, demonstrating `Dependency Injection` 