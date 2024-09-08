package com.example.demo;

import org.springframework.boot.SpringApplication;

public class TestIct4devApplication {

	public static void main(String[] args) {
		SpringApplication.from(Ict4devApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
