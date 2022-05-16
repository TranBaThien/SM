package jp.lg.tokyo.metro.mansion.todokede;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MansionTodokedeApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MansionTodokedeApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MansionTodokedeApplication.class, args);
	}

}
