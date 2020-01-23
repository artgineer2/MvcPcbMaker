package com.mvcpcbmaker;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class AppConfiguration {
	

	@Autowired
	private Environment env;
	

	
	


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
    	try
    	{
            dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
            dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
            dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
            dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
   		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
        return dataSource;
    }
    
    
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	try
    	{
            jdbcTemplate.setResultsMapCaseInsensitive(true);
     	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
        return jdbcTemplate;
    }

    @Bean
    public SimpleJdbcCall simpleJdbcCall(JdbcTemplate jdbcTemplate)
    {
    	SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
    	return simpleJdbcCall;
    }
    
    
    
}
