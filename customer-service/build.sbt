name := "customer-service"

version := "0.1"

scalaVersion := "2.13.5"
val springBootVersion = "2.2.8.RELEASE"
val springBootConfigVersion = "2.2.7.RELEASE"
val springBootNetflixClient="2.2.7.RELEASE"
libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-parent" % springBootVersion,
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion,
  "org.springframework.boot" % "spring-boot-starter-actuator" % springBootVersion,
  "org.springframework.cloud" % "spring-cloud-starter-netflix-eureka-client" % springBootNetflixClient,
  "org.springframework.cloud" % "spring-cloud-starter-config" % springBootConfigVersion,
  "com.google.code.gson"% "gson" % "2.8.5"


)