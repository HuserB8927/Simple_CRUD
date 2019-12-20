package hu.benjaminhalasz.GUI;

import hu.benjaminhalasz.Controller.ApplicantsService;
import hu.benjaminhalasz.Model.Applicants;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.flow.component.dependency.CssImport;


import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;



import java.util.List;

@SpringUI

public class VaadinUI extends UI {

    @Autowired
    private ApplicantsService service;

    private Applicants applicants;

    private Binder<Applicants> binder = new Binder<>(Applicants.class);

    private ApplicationForm form;

    private Grid<Applicants> grid = new Grid(Applicants.class);
    private TextField surname = new TextField("Suername");
    private TextField firstName = new TextField("First name");
    private TextField phone = new TextField("Phone");
    private TextField email = new TextField("Email");
    private TextField country = new TextField("Country");
    private TextField birthDate = new TextField("Birthdate");
    private Button save = new Button("Save", e -> saveApplicants());
    private Button add = new Button("Add", e -> addApplicants());
    private Button addContact = new Button("Add Contact");
    private TextField filterText = new TextField();
    private Button deleteContact = new Button("Delete Contact");
    private Button clearDatabase = new Button("Clear Database", e -> deleteAll());
    private Button export = new Button("Export Database");

    @Override
    protected void init(VaadinRequest request) {
        updateGrid();

        grid.setColumns("id", "surname", "firstName", "phone", "email", "country", "birthDate");
        grid.addSelectionListener(e -> updateForm());
        grid.setWidth("100%");
        grid.addFooterRowAt(0);

        filterText.setPlaceholder("Filter text...");
        //filterText.setClearButtonVisible(true); --> this doesnt want to work
        filterText.setValueChangeMode(ValueChangeMode.EAGER); //ensures that change events are fired immediately when the user types.
        filterText.addValueChangeListener(e -> filterByName());

        addContact.setVisible(true);
        addContact.addClickListener(event -> addNewForm());

        clearDatabase.addStyleName("cleardb");

        binder.bindInstanceFields(this);
        


        HorizontalLayout actions = new HorizontalLayout(filterText, addContact,
                deleteContact, clearDatabase, export);

        HorizontalLayout saveAdd = new HorizontalLayout(save, add);

        VerticalLayout layout = new VerticalLayout(actions, grid,
                surname, firstName, phone, email, country, birthDate, saveAdd);
        
        
        TextField newSurname = new TextField("Surname");
        binder.forField(newSurname).bind(Applicants::getSurname, Applicants::setSurname);
        TextField newFirstName = new TextField("First name");
        binder.forField(newFirstName).bind(Applicants::getFirstName, Applicants::setFirstName);
        TextField newPhone = new TextField("Phone");
        binder.forField(newPhone).bind(Applicants::getPhone, Applicants::setPhone);
        final TextField newEmail = new TextField("Email");
        binder.forField(newEmail)
                .withValidator(new EmailValidator("Are you sure the given value is an email address?"))
                .bind(Applicants::getEmail, Applicants::setEmail);
        TextField newCountry = new TextField("Country");
        binder.forField(newCountry).bind(Applicants::getCountry, Applicants::setCountry);
        TextField newBirthDate = new TextField("Birthdate");
        binder.forField(newBirthDate).bind(Applicants::getBirthDate, Applicants::setBirthDate);
        FormLayout form = new FormLayout(newSurname, newFirstName, newPhone, newEmail, newCountry, newBirthDate);
        
        HorizontalLayout finalLayout = new HorizontalLayout(form);
        
        layout.setSpacing(true);
        setContent(layout);

    }

    private void updateGrid() {
        List<Applicants> applicant = service.findAll();
        grid.setItems(applicant); //To add rows, use the setItems
        setFormVisible(false);
    }

    private void updateForm() {
        if (grid.asSingleSelect().isEmpty()) {
            setFormVisible(false);
        } else {
            applicants = grid.asSingleSelect().getValue();
            binder.setBean(applicants);
            setFormVisible(true);
        }
    }

    private void addNewForm() {

        binder.setBean(applicants);
        setFormVisible(true);

    }

    private void setFormVisible(boolean visible) {
        surname.setVisible(visible);
        firstName.setVisible(visible);
        phone.setVisible(visible);
        email.setVisible(visible);
        country.setVisible(visible);
        birthDate.setVisible(visible);
        save.setVisible(visible);
        add.setVisible(visible);

    }

    private void saveApplicants() {
        service.update(applicants);
        updateGrid();

    }

    private void deleteAll() {
        service.deleteAll(applicants);
        updateGrid();
    }

    private void addApplicants() {
        service.addNew(applicants);
        updateGrid();

    }

    private void filterByName() {

        grid.setItems(service.findAll());

    }

    public void setApplicants(Applicants applicants) {

        /*
        setBean connects the values in the customer object to the corresponding 
        input fields of the form. When the user changes the value of an input field, 
        the value is set in the corresponding instance variable of the customer object.
         */
        binder.setBean(applicants);

        if (applicants == null) { //when the customer is null the form is hidden
            setVisible(false);
        } else { //when it is not null, the form is shown, and keyboard focus is placed on the First name input field to allow immediate typing.
            setVisible(true);
            firstName.focus();
        }

    }
    
}
