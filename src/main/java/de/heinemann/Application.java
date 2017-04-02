package de.heinemann;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		byte[] bytes = new byte[] { 84, 79, 95, 66, 69, 95, 83, 69, 84, 95, 86, 73, 65, 95, 69, 78, 86, 73, 82, 79, 78, 77, 69, 78, 84, 95, 86, 65, 82, 73, 65, 66, 76, 69};
		String secret = new String(bytes);
		
		SpringApplication.run(Application.class, args);
	}
	
}
