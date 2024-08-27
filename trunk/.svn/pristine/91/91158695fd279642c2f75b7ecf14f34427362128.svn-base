package edu.cust;

import java.util.HashMap;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by qh on 2017/4/9.
 */
@Configuration
public class FilterConfig {
	@Bean
	public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
		FilterRegistrationBean<XssFilter> fb = new FilterRegistrationBean<>();
		fb.setFilter(new XssFilter());
		fb.setOrder(1);
		fb.setEnabled(true);
		fb.addUrlPatterns("/*");
		HashMap<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("excludes", "/favicon.ico,/y/.*");
		initParameters.put("isIncludeRichText", "true");
		fb.setInitParameters(initParameters);
		return fb;
	}

}
