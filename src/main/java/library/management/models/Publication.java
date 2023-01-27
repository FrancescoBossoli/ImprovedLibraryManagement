package library.management.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import library.management.exceptions.FieldLengthException;

//abstract class shared between books and magazines
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Setter
@Getter
@NoArgsConstructor
@NamedQuery(name= "getByISBNCode", query = "SELECT p FROM Publication p WHERE p.ISBNCode = :p")
@NamedQuery(name= "getByYear", query = "SELECT p FROM Publication p WHERE p.publishingYear = :p")
@NamedQuery(name= "getByAuthor", query = "SELECT p FROM Publication p WHERE lower(p.author) LIKE CONCAT ('%',:p,'%')")
@NamedQuery(name= "getByTitle", query = "SELECT p FROM Publication p WHERE lower(p.title) LIKE CONCAT ('%',:p,'%')")
@NamedQuery(name= "getCatalog", query = "SELECT p FROM Publication p")
public abstract class Publication {
	@Id
	@GeneratedValue
	private int id;
	@Column(length = 13, nullable = false, unique = true, name = "isbn_code")	
	private String ISBNCode;	
	private String title;
	@Column(name = "publishing_year")
	private int publishingYear;
	@Column(name = "page_number")
	private int pageNumber;
	
	public Publication(String ISBNCode, String title, int publishingYear, int pageNumber) {		
			this.setISBNCode(ISBNCode);
			this.setTitle(title);
			this.setPublishingYear(publishingYear);
			this.setPageNumber(pageNumber);		
	}

	private void setTitle(String title) {
		if (title.length()>2) this.title = title;
		else throw new FieldLengthException("A Publication's Title field can't be less than 3 characters long");
	}

}
