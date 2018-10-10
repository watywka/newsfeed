package ru.newsfeed.trendsoft;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrendsoftApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrendsoftApplication.class, args);
	}

}
