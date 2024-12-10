package com.Files.UserEntity.AOP;

 import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient; 
  
@Component
@Aspect
public class LoggingAspect {

	@Autowired
	WebClient.Builder webClientBuilder;
	
 @Before("execution(* com.Files.UserEntity.Controller.showall(..))")
 public void beforeRegister() {
	 sendLogToGateway("User trying to regioster");
 }
 

 @After("execution(* com.Files.UserEntity.Controller.showall(..))")
 public void afterRegister() {
	 System.out.println("User registered successfully...!!!");
 }
     
 private void sendLogToGateway(String logMessage) {
     webClientBuilder.build()
         .post()
         .uri("http://localhost:8089") // Replace with your Gateway endpoint
         .bodyValue(logMessage)
         .retrieve()
         .bodyToMono(Void.class)
         .subscribe();
 }
}