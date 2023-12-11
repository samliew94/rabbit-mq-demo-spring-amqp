package com.rabbitmqdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitmqDemoApplication implements CommandLineRunner{

	@Value("${demo.duration:0}")
	private int duration;

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqDemoApplication.class, args);
	}

	@Autowired
	ConfigurableApplicationContext ctx;

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Running for " + duration + "ms");
		Thread.sleep(duration);
		ctx.close();

	}

}
