package library.management.utils;

import java.time.LocalDate;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import library.management.dao.LoanDAO;
import library.management.dao.PublicationDAO;
import library.management.dao.UserDAO;
import library.management.enums.Periodicity;
import library.management.exceptions.FieldLengthException;
import library.management.exceptions.UnacceptableDateException;
import library.management.models.*;

public class Menu {
	
	private static Scanner input = new Scanner(System.in);
	private static final Logger logger = LoggerFactory.getLogger(Menu.class);
	private static final PublicationDAO pD = new PublicationDAO();
	private static final LoanDAO lD = new LoanDAO();
	private static final UserDAO uD = new UserDAO();	

	public void populateDatabase() {
		pD.save(new Book("9788804711957", "Il Trono di Spade", 2019, 829, "George R. R. Martin", "Fantasy"));
		pD.save(new Book("9788821561573", "La Sacra Bibbia", 2008, 1968, "CEI", "Religion"));
		pD.save(new Book("9788804746676", "Il Codice da Vinci", 2022, 516, "Dan Brown", "Mistery"));
		pD.save(new Book("9788879843836", "Il viaggio in Occidente", 2013, 1600, "Ch'eng-en Wu", "Fantasy"));
		pD.save(new Magazine("9788891290212", "Monster Deluxe Vol.2", 2019, 400, Periodicity.MONTHLY));
		pD.save(new Magazine("9788862670999", "Heavy Metal", 2022, 128, Periodicity.MONTHLY));
		pD.save(new Magazine("5000089506400", "The Burlington Vol.1308", 2012, 40, Periodicity.MONTHLY));
				
		uD.save(new User("Carlo", "Verdi", LocalDate.parse("1990-09-22")));
		uD.save(new User("Luca", "Neri", LocalDate.parse("1995-01-14")));
		uD.save(new User("Mario", "Bianchi", LocalDate.parse("1993-04-01")));
		uD.save(new User("Lucia", "Monti", LocalDate.parse("1992-09-25")));
		uD.save(new User("Sofia", "Valli", LocalDate.parse("1982-07-30")));
		uD.save(new User("Paolo", "Rossi", LocalDate.parse("1986-10-30")));
		
		lD.save(new Loan(pD.getByISBNCode("9788821561573"), uD.getById(2), LocalDate.parse("2022-12-01")));
		lD.save(new Loan(pD.getByISBNCode("9788804746676"), uD.getById(2), LocalDate.parse("2022-12-20")));
		lD.save(new Loan(pD.getByISBNCode("9788804711957"), uD.getById(1), LocalDate.parse("2022-12-10")));
		lD.save(new Loan(pD.getByISBNCode("9788891290212"), uD.getById(3), LocalDate.parse("2022-12-31")));				
	}
	
	public void mainMenu() {
		logger.info("""
                                   ---------------------------------------------------------------- 
                                   Interactive Library Archive: 
                                   1. Browse the Catalog 
                                   2. Search for a Publication 
                                   3. Add a new Publication to the Library Catalog 
                                   4. Remove a Publication from the Library Catalog 
                                   5. Search for current loans 
                                   6. List all Publications with an expired loan and still missing
                                    
                                   0. Turn Off the Interactive Archive 
                                   """);
			
		int selection = 10;
		outerloop:
		while (selection != 0) {
			try {
				selection = Integer.parseInt(input.nextLine());
				switch (selection) {
				case 1 -> pD.printList(pD.getCatalog());
				case 2 -> {
                                    searchMenu();
                                    selection = Integer.parseInt(input.nextLine());
                                    switch (selection) {
                                        case 1 -> {
                                        	logger.info("Input the ISBN Code");
                                        	logger.info(pD.getByISBNCode(input.nextLine()).toString());
                                        }
                                        case 2 -> {
                                        	logger.info("Input the title or part of it");
                                            pD.printList(pD.getByTitle(input.nextLine()));
                                        }
                                        case 3 -> {
                                        	logger.info("Input the Year of Publishing");
                                            pD.printList(pD.getByYear(Integer.parseInt(input.nextLine())));
                                        }
                                        case 4 -> {
                                        	logger.info("Input the Author");
                                            pD.printList(pD.getByAuthor(input.nextLine()));
                                        }
                                        case 9 -> {}
                                        case 0 -> {
                                            break outerloop;
                                        }
                                    }
                                }
				case 3 -> {
                                    addMenu();
                                    String code, title, author, genre;
                                    int year, pages;
                                    selection = Integer.parseInt(input.nextLine());
                                    switch (selection) {
                                        case 1 -> {
                                        	logger.info("Input the ISBN Code");
                                            code = input.nextLine();
                                            logger.info("Input the Title");
                                            title = input.nextLine();
                                            if (title.length()<3) throw new FieldLengthException("A Publication's Title field can't be less than 3 characters long");
                                            logger.info("Input the Year of Publishing");
                                            year = Integer.parseInt(input.nextLine());
                                            if (year < 1500) throw new UnacceptableDateException("The publishing year can't be too far in the past");
                                            else if (year > 2023) throw new UnacceptableDateException("The publishing year can't be in the future");
                                            logger.info("Input the Number of Pages");
                                            pages = Integer.parseInt(input.nextLine());
                                            logger.info("Input the Author");
                                            author = input.nextLine();
                                            logger.info("Input the Genre");
                                            genre = input.nextLine();
                                            pD.save(new Book(code, title, year, pages, author, genre));
                                        }
                                        case 2 -> {
                                        	logger.info("Input the ISBN Code");
                                            code = input.nextLine();
                                            logger.info("Input the Title");
                                            title = input.nextLine();
                                            if (title.length()<3) throw new FieldLengthException("A Publication's Title field can't be less than 3 characters long");
                                            logger.info("Input the Year of Publishing");
                                            year = Integer.parseInt(input.nextLine());
                                            if (year < 1500) throw new UnacceptableDateException("The publishing year can't be too far in the past");
                                            else if (year > 2023) throw new UnacceptableDateException("The publishing year can't be in the future");
                                            logger.info("Input the Number of Pages");
                                            pages = Integer.parseInt(input.nextLine());
                                            logger.info("Input the periodicity");
                                            try {
                                                Periodicity period = Periodicity.valueOf(input.nextLine().toUpperCase());
                                                pD.save(new Magazine(code, title, year, pages, period));
                                            } catch (IllegalArgumentException e) {
                                            	logger.info("Periodicity can be only expressed as 'Weekly', 'Monthly' or 'Biannual'");
                                            }
                                        }
                                        case 9 -> {}
                                        case 0 -> {
                                            break outerloop;
                                        }
                                    }
                                }
				case 4 -> {
									logger.info("Input the ISBN Code of the Publication you want to delete");
                                    pD.deleteByISBNCode(input.nextLine());
                                    logger.info("Any publications with the specified code have been removed from the archive");
                                }
				case 5 -> {			
									logger.info("Input the User Card ID");
									lD.printList(lD.getByCard(input.nextLine()));									
								}
				case 6 -> {
									lD.printList(lD.getExpiredLoans());
								}
				case 0 -> {
                                    break outerloop;
                                }					
				}
				mainMenu();				
			} catch (NumberFormatException e) {
				logger.info("You have to input a valid option number");
				mainMenu();	
			} catch (UnacceptableDateException | FieldLengthException e) {
				logger.info(e.getMessage());
				mainMenu();
			} catch (IndexOutOfBoundsException e) {
				logger.info("The Library Catalog doesn't seem to contain what you were searching for ");
				mainMenu();
			}
		}		
	}
		
	public void searchMenu() {
		System.out.println("""
                                   ---------------------------------------------------------------- 
                                   What parameter do you want to use in your search? 
                                   1. ISBN Code
                                   2. Title 
                                   3. Year of Publishing 
                                   4. Author 
                                    
                                   9. Go Back 
                                   0. Turn Off the Interactive Archive 
                                   """);
	}
		
	public void addMenu() {
		System.out.println("""
                                   ----------------------------------------------------------------
                                   What kind of Publication do you want to add? 
                                   1. Book 
                                   2. Magazine 
                                    
                                   9. Go Back 
                                   0. Turn Off the Interactive Archive 
                                   """);
	}	
}
