package edu.cust;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataSourceConfig {
	
	@Value("${spring.datasource.inner.jndi-name}")
    private String innerJndiName;

    @Value("${spring.datasource.outer.jndi-name}")
    private String outerJndiName;
    
    @Bean(name="innerDataSource")
	public DataSource innerDataSource() {
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup(innerJndiName);
			return dataSource;
		} catch (Exception ex) {
			log.warn("******{}******", ex.getMessage());
			DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
			ds.setUrl("jdbc:mysql://localhost:3306/ddos?AllowPublicKeyRetrieval=True&serverTimezone=Hongkong&characterEncoding=UTF-8");
			ds.setUsername("ddos");
			ds.setPassword("cust");
			return ds;
		}
	}

    @Bean(name="outerDataSource")
	@Primary
	public DataSource outerDataSource()  {
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup(outerJndiName);
			return dataSource;
		} catch (Exception ex) {
			log.warn("******{}******", ex.getMessage());
			DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
			ds.setUrl("jdbc:mysql://localhost:3306/ddos?AllowPublicKeyRetrieval=True&serverTimezone=Hongkong&characterEncoding=UTF-8");
			ds.setUsername("ddos");
			ds.setPassword("cust");
			return ds;
		}
	}

	@Bean(name = "jt")
	public JdbcTemplate innerJdbcTemplate() {
		return new JdbcTemplate(innerDataSource());
	}

	@Bean(name = "outerJt")
	public JdbcTemplate outerJdbcTemplate() {
		return new JdbcTemplate(outerDataSource());
	}

}
