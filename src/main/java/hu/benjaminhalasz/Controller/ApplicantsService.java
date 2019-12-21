
package hu.benjaminhalasz.Controller;
import hu.benjaminhalasz.Model.Applicants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class ApplicantsService {

    
    
    private final HashMap<Long, Applicants> applicants = new HashMap<>();
    private static ApplicantsService instance;
    
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Applicants> findAll() {
        return jdbcTemplate.query(
                "SELECT id, surname, firstName, phone, email, country, birthDate FROM applicants",
                (rs, rowNum) -> new Applicants(
                        rs.getLong("id"), 
                        rs.getString("surname"), 
                        rs.getString("firstName"), 
                        rs.getString("phone"),
                        rs.getString("email"), 
                        rs.getString("country"), 
                        rs.getString("birthDate")));
    }

    public void update(Applicants applicants) {
        jdbcTemplate.update("UPDATE applicants SET surname=?, firstName=?, phone=?, email=?, country=?, birthDate=? WHERE id=?",
                applicants.getSurname(), 
                applicants.getFirstName(), 
                applicants.getPhone(),
                applicants.getEmail(), 
                applicants.getCountry(), 
                applicants.getBirthDate(), 
                applicants.getId());
    }

    public void addNew(Applicants applicants) {
        String sql = "INSERT INTO applicants (surname, firstName, phone, email, country, birthDate) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, 
                applicants.getSurname(), 
                applicants.getFirstName(),
                applicants.getPhone(), 
                applicants.getEmail(), 
                applicants.getCountry(), 
                applicants.getBirthDate());

    }
    public static ApplicantsService getInstance() {
        if (instance == null) {
			instance = new ApplicantsService();
			instance.findAll();
		}
		return instance;
	
    }
    public synchronized List<Applicants> findByInput() {
		return findByInput(null);
	}

    public synchronized List<Applicants> findByInput(String stringFilter) {
		ArrayList<Applicants> arrayList = new ArrayList<>();
		for (Applicants applicant : applicants.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| applicant.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(applicant.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(ApplicantsService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Applicants>() {

			@Override
			public int compare(Applicants a1, Applicants a2) {
				return (int) (a2.getId() - a1.getId());
			}
		});
		return arrayList;
	}

    public void deleteAll(Applicants applicants) {
           String sql = "TRUNCATE TABLE applicants";
        jdbcTemplate.update(sql);
    
    }
    public void delete(Applicants applicants) {
        String sql = "DELETE FROM applicants WHERE id=?";
        jdbcTemplate.update(sql, applicants.getId());
    }
    
}
