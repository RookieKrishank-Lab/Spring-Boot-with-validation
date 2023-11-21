package com.springbootrestapi;

import com.springbootrestapi.entity.User;
import com.springbootrestapi.mapper.UserMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpringBootRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiApplication.class, args);
	}

	@Bean
	public UserMapper mapper(){
		UserMapper map = new UserMapper();
		return map;
	}
}