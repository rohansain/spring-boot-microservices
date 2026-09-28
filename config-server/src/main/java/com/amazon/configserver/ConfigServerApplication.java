package com.amazon.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	static {
		// Detect whether .env is in the current directory or the parent directory
		String envDir = new File(".env").exists() ? "./" : "../";

		 Dotenv dotenv = Dotenv.configure()
				.directory(envDir)
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);
	}

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
