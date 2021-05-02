package com.victor.cursohibernate.config;

import com.victor.cursohibernate.services.DBService;
import com.victor.cursohibernate.services.EmailService;
import com.victor.cursohibernate.services.MockEmailService;
import com.victor.cursohibernate.services.SMTPEmailService;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig
{
	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public Boolean instantiateDatabase() throws ParseException
	{
		if(!"create".equals(strategy))
		{
			return false;
		}

		dbService.instantiateTestDatabase();
		return true;
	}

	@Bean
	public EmailService emailService()
	{
//		return new MockEmailService();
		return new SMTPEmailService();
	}

}
