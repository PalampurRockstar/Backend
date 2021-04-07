package com.backend


import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

object CustomerServiceApplication {
  def main(args: Array[String]) : Unit = SpringApplication.run(classOf[CustomerServiceApplication], args :_ *)
}
@RestController
@SpringBootApplication
@EnableEurekaClient
class CustomerServiceApplication  @Value("${description}")(description: String){

  @RequestMapping(path = Array("/test"), method = Array(RequestMethod.GET), produces = Array(MediaType.TEXT_PLAIN_VALUE))
  @ResponseBody
  def test ():String={

    description+" working!"
  }
}