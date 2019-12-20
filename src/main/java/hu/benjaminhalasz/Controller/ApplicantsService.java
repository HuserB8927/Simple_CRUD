
package hu.benjaminhalasz.Controller;
import hu.benjaminhalasz.Model.Applicants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;


@Component
public class ApplicantsService {
    
    
    
	
	

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Applicants> findAll() {
        return jdbcTemplate.query(
                "SELECT id, surname, firstName, phone, email, country, birthDate FROM applicants",
                (rs, rowNum) -> new Applicants(rs.getLong("id"), rs.getString("surname"), rs.getString("firstName"), rs.getString("phone"),
                        rs.getString("email"), rs.getString("country"), rs.getString("birthDate")));
    }

    public void update(Applicants applicants) {
        jdbcTemplate.update("UPDATE applicants SET surname=?, firstName=?, phone=?, email=?, country=?, birthDate=? WHERE id=?",
                applicants.getSurname(), applicants.getFirstName(), applicants.getPhone(),
                applicants.getEmail(), applicants.getCountry(), applicants.getBirthDate(), applicants.getId());
    }

    public void addNew(Applicants applicants) {
        String sql = "INSERT INTO applicants (surname, firstName, phone, email, country, birthDate) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, applicants.getSurname(), applicants.getFirstName(),
                applicants.getPhone(), applicants.getEmail(), applicants.getCountry(), applicants.getBirthDate());

    }

    public void findById() {

    }

    public void deleteAll(Applicants applicants) {
           String sql = "TRUNCATE TABLE applicants";
        jdbcTemplate.update(sql);
    
    }
    
}
