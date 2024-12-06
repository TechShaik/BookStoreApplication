package com.files.BookStoreGateway.Filter;

 
 
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.files.BookStoreGateway.config.util.JwtUtil;
 
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

	@Autowired
	private RouteValidator routeValidator;
	
//	@Autowired
//	private RestTemplate restTemplate;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public AuthenticationFilter() {
		super(Config.class);
	}

	public static class Config{
		 
	}

	@Override
	public GatewayFilter apply(Config config) {
		 return ((exchange,chain)->{
			 if(routeValidator.isSecured.test(exchange.getRequest())) {
				 if(!exchange.getRequest().getHeaders().containsKey( org.springframework.http.HttpHeaders.AUTHORIZATION)) {
					 throw new RuntimeException("Missing authorization");
			 	 }
				 
				 String authHeader=exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				 if(authHeader!=null && authHeader.startsWith("Bearer ")) {
					 authHeader=authHeader.substring(7);
				 }
				 try {
					//restTemplate.getForObject("http://BOOKSTOREGATEWAY//users/validate?token"+authHeader, String.class); 
				    jwtUtil.validateToken(authHeader);
				 
				 }catch(Exception ex){
					 System.out.println("Invalid access...");
					 throw new RuntimeException("Unauthorized access to application");
				 }
			 }
			 //check token in header
			 
			 return chain.filter(exchange);
		 });
	}
	
	
}
