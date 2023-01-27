package library.management.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
@NamedQuery(name= "getByCard", query = "SELECT l FROM Loan l WHERE l.user.card = :c AND l.bookRetrieval IS NULL")
@NamedQuery(name= "getExpiredLoans", query = "SELECT l FROM Loan l WHERE l.bookRetrieval IS NULL AND l.loanEnd < :d")
public class Loan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Publication publication;
    @OneToOne
    private User user;
    @Column(name = "loan_start")
    private LocalDate loanStart;
    @Column(name = "loan_due_date")
    private LocalDate loanEnd;
    @Column(name = "book_retrieval")
    private LocalDate bookRetrieval;
    // This method should be used just for the first load. A loan should be created with the date corresponding to the day of the loan
    public Loan(Publication p, User b, LocalDate d) {
    	setPublication(p);
        setUser(b);
        setLoanStart(d);
        setLoanEnd(loanStart.plusDays(30));    	
    }
    
    public Loan(Publication p, User b) {
        this(p, b, LocalDate.now());       
    }
    
    @Override
    public String toString() {
    	return "Loan ID: " + getId() + " | User: " + getUser().getId() + " | Publication: "
    			+ String.format("%1$-"+ 24 + "s", getPublication().getTitle()) + " | Loan Date: " + getLoanStart();    
    }
}
