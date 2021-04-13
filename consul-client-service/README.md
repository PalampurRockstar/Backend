__Intro__ : Demonstrate consul client server in spring boot microservices
##### Custom connection properties
```
spring:
  cloud:
    consul:
      host: localhost
      port: 8500
```

Run consul server 
Steps :
1. create directory `consul-data` 
2. `consul agent -server -bootstrap-expect=1 -data-dir ./consul-data -ui -bind=<ip address>`
