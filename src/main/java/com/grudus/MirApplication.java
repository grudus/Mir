package com.grudus;

import com.grudus.dao.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MirApplication {

	public static void main(String[] args) {
		SpringApplication.run(MirApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository ur) {
		return args -> {
			ur.findAll().forEach(System.out::println);
			System.out.println(ur.findByLogin("Dupny2"));
		};
	}
}
