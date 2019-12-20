/*
 This class would represent a new form to input and add new contcact to Applicants. 
Probably this class is not important, I can manage everything from VaadinUI.java
 */
package hu.benjaminhalasz.GUI;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

/**
 *
 * @author benjaminhalasz
 */
public class ApplicationForm extends FormLayout {
    
    private TextField surname = new TextField("Suername");
    private TextField firstName = new TextField("First name");
    private TextField phone = new TextField("Phone");
    private TextField email = new TextField("Email");
    private TextField country = new TextField("Country");
    private TextField birthDate = new TextField("Birthdate");
    
    private Button save = new Button("Save");
    private Button add = new Button("Add");
    private Button delete = new Button("Delete");
    
    public ApplicationForm() {
    
    HorizontalLayout buttons = new HorizontalLayout(save, add, delete);
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    add(surname, firstName, phone, email, country, birthDate, buttons);
    }

}
