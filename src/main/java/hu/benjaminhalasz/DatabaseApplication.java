package hu.benjaminhalasz;


import com.vaadin.annotations.StyleSheet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@StyleSheet("style.css")
@SpringBootApplication
public class DatabaseApplication {
    
    

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
                
                
	}

}
