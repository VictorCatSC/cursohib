package com.victor.cursohibernate.config;

import com.victor.cursohibernate.services.DBService;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig
{
	@Autowired
	private DBService dbService;

	@Bean
	public Boolean instantiateDatabase() throws ParseException
	{
		dbService.instantiateTestDatabase();
		return true;
	}
}
