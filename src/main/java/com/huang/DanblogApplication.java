package com.huang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.huang.mapper"})
public class DanblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanblogApplication.class, args);
	}

}
