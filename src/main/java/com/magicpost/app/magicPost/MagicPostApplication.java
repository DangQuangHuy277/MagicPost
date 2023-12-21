package com.magicpost.app.magicPost;

import com.magicpost.app.magicPost.security.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
public class MagicPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagicPostApplication.class, args);
	}

}
