package cs251.group9.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BackendApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	
	//Build War File for the Tomcat
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BackendApplication.class);
	}

}
