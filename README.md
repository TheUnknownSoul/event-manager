# event-manager
The project is “Building asynchronous Events Subsystem using Pub/Sub
Patterns”
Let us assume three Applications - App A, App B and App C etc.
All 3 applications publish 3 events each and subscribe to two events each:
App A publishes A1, A2, A3 as Events and subscribes to B1 and C2 as Events.
App B publishes B1, B2, B3 as Events and subscribes to C1 and A2 as Events.
App C publishes C1, C2, C3 as Events and subscribes to A1 and B2 as events

EventManager microservice have four
endpoints:

`/register (one time initialisation to be called by any new publisher)`

`/subscribe (one time initialisation to be called by any new subscriber)`

`/send (called by publishing Apps for each event post)`

`/receive (call back method to be notified on arrival of every event post to Subscribed Apps)`
# Basic algorithm and how it works
This microservice allows publishing messages(events) as author and receive it as subscriber. 
### Publisher
Application register on the endpoint like this:
`localhost:8080/publisher/registration?name=appA`,
where "name"  it\`s like application id.Then, if your application have subscriber they will be able to receive messages (events) through the RabbitMQ server. If don`t have, messages will be saved in RabbitMQ exchange. 
(By default type of exchange - topic). 
### Subscriber
Subscriber can view list of all publishers on endpoint: 
`http://localhost:8080/subscriber/publishers` ,
and subscribe to favourite. To subscribe just call endpoint: 
`http://localhost:8080/subscriber/subscribe?appId={name of your app}&subscriberName={your name as subscriber}`.
After that, queue for subscribed will be created. Receiving messages will be able on
`http://localhost:8080/subscriber/receive?name` endpoint.

### How to get it
1.Download and install RabbitMQ from https://www.rabbitmq.com/download.html the version for your OS;

2.Clone this project to your IDE;

3.Run `rabbitmq-server.bat` and application;

4.Test endpoints which given earlier in postman.

To view the results of code you should enable RabbitMQ management plugin and open `http://localhost:15672/` webpage.
Notes, that default username and password is "guest". 