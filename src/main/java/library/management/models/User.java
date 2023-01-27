
package library.management.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id ;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String card;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@OrderBy(value = "loan.loanStart")
    private List<Loan> loans;
    
    public User(String n, String s, LocalDate b) {
    	setName(n);
    	setSurname(s);
    	setBirthdate(b);
    }
}
