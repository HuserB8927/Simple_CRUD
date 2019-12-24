package hu.benjaminhalasz.ui;



import com.vaadin.annotations.StyleSheet;
import hu.benjaminhalasz.service.ApplicantsService;
import hu.benjaminhalasz.entity.Applicants;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


import com.vaadin.icons.VaadinIcons;


import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.ui.themes.ValoTheme;




import java.util.List;


@CssImport("styles/style.css")
@SpringUI
public class VaadinUI extends UI implements HasStyle {

    @Autowired
    private ApplicantsService service;

    private Applicants applicants;

    private Binder<Applicants> binder = new Binder<>(Applicants.class);

    private Grid<Applicants> grid = new Grid(Applicants.class);
    private TextField surname = new TextField("Suername");
    private TextField firstName = new TextField("First name");
    private TextField phone = new TextField("Phone");
    private TextField email = new TextField("Email");
    private TextField country = new TextField("Country");
    private TextField birthDate = new TextField("Birthdate");
    private Button save = new Button("Save", e -> saveApplicants());
    private Button add = new Button("Add", e -> addApplicants());
    private Button delete = new Button("Delete", e -> deleteApplicants());
    private Button addContact = new Button("Add Contact");
    private TextField filterText = new TextField();
    private Button clearDatabase = new Button("Clear Database", e -> deleteAll());
 

    @Override
    protected void init(VaadinRequest request) {
        applicants = new Applicants(null, "","","","","",""); //this way we have an empty object bound to the UI
        binder.setBean(applicants); 
        
        updateGrid();

        grid.setColumns("id", "surname", "firstName", "phone", "email", "country", "birthDate");
        grid.addSelectionListener(e -> updateForm());
        grid.setWidth("100%");
        grid.addFooterRowAt(0);

        filterText.setPlaceholder("Filter text...");
       // filterText.setClearButtonVisible(true); // --> this doesnt want to work
        filterText.setValueChangeMode(ValueChangeMode.LAZY); //ensures that change events are fired immediately when the user types.
        filterText.addValueChangeListener(e -> filterDatas());
        
        Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
       
        clearFilterTextBtn.setDescription("Clear the current filter...");
        clearFilterTextBtn.addClickListener(e -> filterText.clear());
        
        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        
        
        
        
        
        addContact.setVisible(true);
        addContact.addClickListener(event -> addNewForm());

        clearDatabase.addStyleName("cleardb");

        binder.bindInstanceFields(this);
        
        binder.forField(email)
                .withValidator(new EmailValidator("Are you sure the given value is an email address?"))
                .bind(Applicants::getEmail, Applicants::setEmail);
       

        
        HorizontalLayout actions = new HorizontalLayout(filtering, addContact, clearDatabase);

        HorizontalLayout saveAdd = new HorizontalLayout(save, add, delete);

        
        
        
        VerticalLayout layout = new VerticalLayout(actions, grid,
                surname, firstName, phone, email, country, birthDate, saveAdd);
        
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
        delete.setVisible(visible);
       
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

    public void filterDatas() {
        
            service.findByName(filterText.getValue());
         
     updateGrid();
       
    }
    public void deleteApplicants() {
        service.delete(applicants);
        updateGrid();
    
}

    @Override
    public Element getElement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}