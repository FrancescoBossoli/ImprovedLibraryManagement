package library.management.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import library.management.exceptions.FieldLengthException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "book")
@Table(name = "books")
@Getter
@NoArgsConstructor
public class Book extends Publication {
	
	private String author;
	private String genre;
	
	public Book(String ISBNCode, String title, int publishingYear, int pageNumber, String author, String genre) {
		super(ISBNCode, title, publishingYear, pageNumber);
		setAuthor(author);
		setGenre(genre);
	}
	
	private void setAuthor(String author) {
		if (author.length()>2) this.author = author;		
		else throw new FieldLengthException("A Publication's Author field can't be less than 3 characters long");
	}

	private void setGenre(String genre) {
		if (genre.length()>2) this.genre = genre;		
		else throw new FieldLengthException("A Publication's Title can't be less than 2 characters long");
	}
	
	@Override
	public String toString() {
		return "Book Details:     ISBNCode: " + this.getISBNCode() + " | Title: " + String.format("%1$-"+ 24 + "s", this.getTitle()) 
			 + "| Year of Publication: " + this.getPublishingYear() + " | Page Count: " + String.format("%1$-"+ 5 + "s",this.getPageNumber()) 
			 + "| Author: " + String.format("%1$-"+ 20 + "s",this.getAuthor()) + "| Genre: " + this.getGenre();		
	}
	
   
}
