package application;

import java.io.IOException;

/* Class that starts the main program */
public class App {

	public static void main(String[] args) throws IOException {
		
		// This is the starting point of the project!
		// hint - It usually calls method from GameManager class to start the app, so only one or two lines will be written here

		GameManager gameManager = new GameManager();
		gameManager.start();
	}

}