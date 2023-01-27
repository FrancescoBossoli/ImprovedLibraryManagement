
package library.management;


import library.management.utils.Menu;

public class LibraryManagement {
	
	private static final Menu display = new Menu();

	public static void main(String[] args) {
		//necessary method to have data in the catalog on first execution
		display.populateDatabase();
		
		display.mainMenu();
	}
    
}
