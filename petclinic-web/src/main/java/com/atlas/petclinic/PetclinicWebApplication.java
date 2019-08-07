/**
 * 
 */
package com.atlas.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.antheminc.oss.nimbus.domain.session.HttpSessionProvider;
import com.antheminc.oss.nimbus.domain.session.SessionProvider;

/**
 * @author Jayant Chaudhuri
 *
 */
@Configuration
@SpringBootApplication(scanBasePackageClasses=LoginController.class)
@ComponentScan
@EnableAutoConfiguration
public class PetclinicWebApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){ 
		return application.sources(PetclinicWebApplication.class);
	} 
	
	public static void main(String[] args) throws Exception { 
		SpringApplication.run(PetclinicWebApplication.class, args);
	} 
	
	@Bean
	public SessionProvider sessionProvider() { 
		return new HttpSessionProvider();
	} 
	
	@RequestMapping("/petclinicweb-test/{name}")
	public String test(@PathVariable String name) {
		return "Hello "+name;
	}

}