package com.grudus;

import com.grudus.dao.MessageRepository;
import com.grudus.dao.UserRepository;
import com.grudus.entities.Message;
import com.grudus.help.MessageHelp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication
public class MirApplication {

	public static void main(String[] args) {
		SpringApplication.run(MirApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository ur, MessageRepository mr) {
		return args -> {
			mr.findAll().forEach(System.out::println);
		};
	}
}
