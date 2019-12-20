package hu.benjaminhalasz;

import com.vaadin.flow.component.dependency.CssImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@CssImport("styles/my-custom.css")
public class DatabaseApplication {
    
    

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
                
                
	}

}
