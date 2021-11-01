package com.untangle.wordcount;

import com.untangle.wordcount.model.Tracker;
import com.untangle.wordcount.service.WordCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class WordCountApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(WordCountApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WordCountApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.trace("Entering run method with {} args", args.length);
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.print("Please input the name of a file on the classpath. If you want to try a new file, " +
					"add it into src/main/resource. example1.txt is the only option currently " +
					"and is the assignment description: ");
			String filename = in.nextLine();
			try {
				HashMap<String, Tracker> result = WordCountService.analyzeFileFromClasspath(filename);
				String format = "| %-5s | %-20s | %-20s |\n";
				String[] keys = Stream.of(result.keySet().toArray(new String[result.keySet().size()])).sorted().toArray(String[]::new);
				System.out.println("|-------|----------------------|---------------------|");
				System.out.printf(format, "#", "word", "{ count:sentences }");
				for(int i = 0; i < keys.length; i++) {
					String key = keys[i];
					Tracker tracker = result.get(key);
					String indexes = tracker.getSentenceIndex().stream().map(String::valueOf).collect(Collectors.joining(","));
					System.out.printf(
							format,
							String.format("%s.", toAlphabetic(i).toLowerCase()),
							key,
							String.format("{ %d : %s }", tracker.getCount(), indexes));
				}
			} catch (FileNotFoundException fnfe) {
				System.out.printf("The file %s wasn't found on the classpath, please try again.\n", filename);
				logger.error(String.format("FileNotFound Exception from trying to parse the file: %s", filename), fnfe);
			} catch (Exception e) {
				System.out.printf("An exception occurred, please try again: %s", e.getMessage());
				logger.error(String.format("An exception occured trying to parse the file: %s", filename), e);
			}
		}
	}

	//Stolen from https://stackoverflow.com/questions/10813154/how-do-i-convert-a-number-to-a-letter-in-java
	public static String toAlphabetic(int i) {
		int quot = i/26;
		int rem = i%26;
		char letter = (char)((int)'A' + rem);
		if( quot == 0 ) {
			return ""+letter;
		} else {
			return toAlphabetic(quot-1) + letter;
		}
	}
}
