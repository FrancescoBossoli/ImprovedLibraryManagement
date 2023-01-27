package library.management.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import library.management.enums.Periodicity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "magazine")
@Table(name = "magazines")
@Getter
@Setter
@NoArgsConstructor
public class Magazine extends Publication {
	
	@Enumerated(EnumType.STRING)
	private Periodicity periodicity;	

	public Magazine(String ISBNCode, String title, int publishingYear, int pageNumber, Periodicity periodicity) {
		super(ISBNCode, title, publishingYear, pageNumber);		
			setPeriodicity(periodicity);				
	}
		
	@Override
	public String toString() {
		return "Magazine Details: ISBNCode: " + this.getISBNCode() + " | Title: " + String.format("%1$-"+ 24 + "s", this.getTitle()) 
			 + "| Year of Publication: " + this.getPublishingYear() + " | Page Count: " + String.format("%1$-"+ 5 + "s",this.getPageNumber()) 
			 + "| Periodicity: " + getPeriodicity();		
	}	
}
