package com.grudus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
//@EnableAsync
public class MirApplication {

	public static void main(String[] args) {
		SpringApplication.run(MirApplication.class, args);
	}

/*	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			System.out.println("Przed wyslaniem");
			new EmailSender().send("NO SIEMA", "kubagruda2@gmail.com");};
	}*/

}
