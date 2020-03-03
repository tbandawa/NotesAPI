package me.tbandawa.notesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NotesAPI {

	public static void main(String[] args) {
		SpringApplication.run(NotesAPI.class, args);
	}

}
