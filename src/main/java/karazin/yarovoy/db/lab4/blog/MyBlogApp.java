package karazin.yarovoy.db.lab4.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MyBlogApp {

	public static void main(String[] args) {
		SpringApplication.run(MyBlogApp.class, args);
	}

}
