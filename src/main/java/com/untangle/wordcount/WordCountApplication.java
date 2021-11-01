package com.untangle.wordcount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WordCountApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(WordCountApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WordCountApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
