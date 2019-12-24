
package hu.benjaminhalasz.service;
import hu.benjaminhalasz.entity.Applicants;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.core.RowMapper;


@Component
public class ApplicantsService {

    
//private EntityManager em;
//
//    CriteriaBuilder cb = em.getCriteriaBuilder();
//    
//    public List<Applicants> find() {
//        return jdbcTemplate.query(
//    "SELECT * FROM applicants WHERE id = ? "
//            + "AND surname LIKE %?% AND firstName LIKE %?% "
//            + "AND phone = ? AND email = ? "
//            + "AND country = ? AND birthday = ? ",
//            (rs, rowNum) -> new Applicants(
//                        rs.getLong("id"), 
//                        rs.getString("surname"), 
//                        rs.getString("firstName"), 
//                        rs.getString("phone"),
//                        rs.getString("email"), 
//                        rs.getString("country"), 
//                        rs.getString("birthDate")));
//    }
    
    
    //private final HashMap<Long, Applicants> applicants = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(ApplicantsService.class.getName());
    private static ApplicantsService instance;
    private static Applicants applicant = new Applicants(null, "", "", "", "", "", "");
    
    
    
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
//    public synchronized List<Applicants> find() {
//		return find(null);
//	}

    public List<Applicants> find(String stringFilter) {
		ArrayList<Applicants> arrayList = new ArrayList<>();
              
                
                
                        
                
		try {
                    String sql = "SELECT * FROM applicants WHERE surname LIKE '%k%'";
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| applicant.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					//arrayList.add(applicant.clone());
                                        jdbcTemplate.update(sql);
                            
                        arrayList.add(applicant.clone());
                                }
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(ApplicantsService.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
    
    public List<Applicants> findByName(String filterText) {
        Applicants applicants1 = new Applicants(null, "", "", "", "", "", "");
        
        String sql = "SELECT id, surname, firstName, phone, email, country, birthDay from applicants WHERE surname LIKE '%filterText%'";
    
    RowMapper mapper = (RowMapper) (ResultSet rs, int rowNum) -> 
    {
        
            applicants1.setId(rs.getLong("id"));
            applicants1.setSurname(rs.getString("surname"));
            applicants1.setFirstName(rs.getString("firstName"));
            applicants1.setPhone(rs.getString("phone"));
            applicants1.setEmail(rs.getString("email"));
            applicants1.setCountry(rs.getString("country"));
            applicants1.setBirthDate(rs.getString("birthDate"));
            return applicants1;
        };
        return (List<Applicants>) (Applicants) jdbcTemplate.queryForObject(sql, mapper, new Object[] {String.valueOf("")});
        
    
            
    }
 
}

            
      
    


        
        
        
        
        
        
        
        
        
        
        
        
        
//        String sql = "SELECT * FROM applicants WHERE surname LIKE '%?%'";
//        jdbcTemplate.update(sql,
//                applicants.getSurname(), 
//                applicants.getFirstName(),
//                applicants.getPhone(), 
//                applicants.getEmail(), 
//                applicants.getCountry(), 
//                applicants.getBirthDate());
//    }
//    
//}
