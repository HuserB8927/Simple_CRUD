package hu.benjaminhalasz;


import com.vaadin.annotations.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@StyleSheet("context://styles/style.css")

@SpringBootApplication

public class DatabaseApplication extends Div {
    
    

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
                
                
	}

}
