package cz.kubasek.zonkybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZonkyBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZonkyBotApplication.class, args);
	}
}
