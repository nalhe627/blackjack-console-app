package userInterface;

import java.util.Scanner;
import java.util.ArrayList;

import saveableObjects.Player;
import gameComponents.Card;

public class Menu {

	/**
	 * This class will be used to show the menus and sub menus to the user
	 * It also prompts the user for the inputs and validates them 
	 */

	// Attribute
	Scanner keyboard = new Scanner(System.in); // One scanner created so no need to make more

	 /**
	  * Method that displays the initial menu of the program.
	  * @return The choice the user has made
	  */
	 public String startMenu() {

		System.out.println("\n\nSelect one of these options:\n\n" + 
		"\t(P) Play game\n" +
		"\t(S) Search\n" +
		"\t(E) Exit\n");

		return menuChoice("p", "s", "e");
	 }
	 
	 /**
	  * Method that asks the user for their choice in either the main or search menu. Also validates the input
	  * @param firstOption - The first option of the menu
	  * @param secondOption - The second option of the menu
	  * @param thirdOption - The third option of the menu
	  * @return - The choice of the user
	  */
	 public String menuChoice(String firstOption, String secondOption, String thirdOption) {
		System.out.print("Enter a choice: ");
		String choice = keyboard.nextLine().toLowerCase(); // Makes it so that user's choice is case insensitive
		
		while (!(choice.equals(firstOption) || choice.equals(secondOption) || choice.equals(thirdOption))) {
			System.out.println("Invalid choice, must enter either " +
			firstOption.toUpperCase() + ", " + 
			secondOption.toUpperCase() + ", or " + 
			thirdOption.toUpperCase() + ".");
			System.out.print("\nEnter a choice: ");

			choice = keyboard.nextLine().toLowerCase();
		}

		return choice;
	 }

	/**
	 * Method that prompts the user for their name
	 * @return String of the user's name
	 */
	 public String startGameMenu() {
		System.out.print("\nWhat is your name: ");
		String name = keyboard.nextLine();

		// Return their name with a uppercase letter at the beginning lowercase letters for the rest
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	/**
	 * Method that prints out the borders of the rows of the blackjack table.
	 * Also works for the top players and player info table
	 * @param str - String to be repeated
	 * @param count - Amount of times to print the character
	 * @param loop - Amount of iterations of the loop
	 */
	public void repeatTable(String str, int count, int loop) {
		for (int i = 0; i < loop; i++) {
			System.out.print('+' + str.repeat(count));
		}
		System.out.println('+');	
	}

	/**
	 * Method that displays the user's name and money.
	 * @param newPlayer - Boolean that indicates whether the player is new or not
	 * @param player - Player object of the player
	 */
	public void welcomePrompt(boolean newPlayer, Player player) {
		System.out.println("\n" + "*".repeat(73));
		if (newPlayer) {
			System.out.println("***\tWelcome " + player.getName().toUpperCase() + "\t---\t" +
			"Your initial balance is: " + player.getMoney() + " $      ***");
		}
		else { // If the user is an existing player
			System.out.printf("***\tWelcome back " + player.getName().toUpperCase() + "\t---\t" +
			"Your balance is: %-12s ***\n", player.getMoney() + " $");
		}
		System.out.println("*".repeat(73));
	}

	/**
	 * Method that displays to the user that they are broke
	 */
	public void displayNoMoney() {
			System.out.print("\n\nCannot play with a balance of $0. ");
		}
	
	/**
	 * Method that starts the blackjack game by prompting the user for their bet
	 * @param balance - Balance of the player
	 * @return - Float that represents the user's bet
	 */
	public float getBetFromPlayer(float balance) {
		System.out.print("\n\nHow much do you want to bet this round? ");
		String bet = keyboard.nextLine();

		while (bet.equals("0") || bet.contains(".") || Float.parseFloat(bet) > balance) {
			System.out.println("\nInvalid bet; must be a whole number greater than 0, and not greater than your balance");
			System.out.print("\nHow much do you want to bet this round? ");
			bet = keyboard.nextLine();
		}

		return Float.parseFloat(bet);
	}
	
	/**
	 * Method that displays the whole blackjack table (which shows the cards of the player and dealer)
	 * @param playerHand - ArrayList of Cards in player's hand
	 * @param dealerHand - ArrayList of Cards in dealer's hand
	 */
	public void blackJackTable(ArrayList<Card> playerHand, ArrayList<Card> dealerHand) {
		System.out.println("\n\t\t  - BLACKJACK -");
		repeatTable("=", 23, 2);
		System.out.printf("||PLAYER\t\t|%-21s ||\n", "DEALER");
		repeatTable("=", 23, 2);
		
		if (dealerHand.size() >= playerHand.size()) {
			for (int i = 0; i < playerHand.size(); i++) {
				System.out.printf("| %-21s|| %-21s |\n", playerHand.get(i), dealerHand.get(i));
				repeatTable("-", 23, 2);
			}
			if (dealerHand.size() > playerHand.size()) {
				for (int i = 0; i < (dealerHand.size() - playerHand.size()); i++) {
					System.out.printf("| %-21s|| %-21s |\n", "", dealerHand.get(i + playerHand.size()));
					repeatTable("-", 23, 2);
				}
			}
		}
		else if (playerHand.size() > dealerHand.size()) {
			for (int i = 0; i < dealerHand.size(); i++) {
				System.out.printf("| %-21s|| %-21s |\n", playerHand.get(i), dealerHand.get(i));
				repeatTable("-", 23, 2);
			}
			for (int i = 0; i < (playerHand.size() - dealerHand.size()); i++) {
				System.out.printf("| %-21s|| %-21s |\n", playerHand.get(i + dealerHand.size()), "");
				repeatTable("-", 23, 2);
			}
		}
	}

	/**
	 * Method that displays who won the blackjack game, or neither.
	 * After displaying, asks the player if they want to continue playing or not
	 * @param winner - Person that won (or none)
	 * @param bet - Amount of money that the player bet on
	 * @return String that is either "y" or "n"
	 */
	public String displayResult(String winner, float bet) {
		// Check what message to display based on winner
		switch (winner) {
			case "player":
				System.out.println("\nYou win $" + (int) bet);
				break;
			case "dealer":
				System.out.println("\nYou lose $" + (int) bet);
				break;
			case "tie":
				System.out.println("\nIt's a tie, collect your $" + (int) bet);
				break;
			case "natural":
				System.out.println("\nYou win 1.5x your bet, which is $" + bet);
				break;
		}

		return yesOrNo("y", "n", "\nDo you want to play again(y/n)? ");
	}

	/**
	 * Method that prompts the user to choose between two options, then validates their input.
	 * Mainly used for the yes or no prompt, but also works for the hit or stand prompt
	 * @param firstOption - String that represents the first option
	 * @param secondOption - String that represents the second option
	 * @param loopText - Text to continue looping if the user enters an invalid input
	 * @return The choice that the user has made
	 */
	public String yesOrNo(String firstOption, String secondOption, String loopText) {
		System.out.println(loopText);
		System.out.print("\nYour choice: ");
		String choice = keyboard.nextLine().toLowerCase();
		
		while (! (choice.equals(firstOption) || choice.equals(secondOption))) {
			System.out.println("Invalid choice, must be either " + firstOption + " or " + secondOption);	
			System.out.println(loopText);
			System.out.print("\nYour choice: ");
			
			choice = keyboard.nextLine().toLowerCase();
		}
		return choice;
	}

	/**
	 * Method that displays to the user that they're being returned to the main menu
	 */
	public void returnMenu() {
		System.out.println("Returning to main menu...");
	}

	/**
	 * Method that displays the possible options the user can do in the search menu
	 * @return The string of the choice the user has chosen
	 */
	 public String searchMenu() {
		System.out.println("\n\nSelect one of these options:\n\n" +
		"\t (T) Top Player (Most number of wins)\n" +
		"\t (N) Looking for a Name\n" +
		"\t (B) Back to menu\n");
		
		return menuChoice("t", "n", "b");
	}
	
	/**
	 * Method that asks the user for a name they would like to find in the database
	 * @return The string of the name that the user inputs
	 */
	public String findPlayerInput() {
		System.out.print("\nWhat is the name of the person you are looking for: ");
		String name = keyboard.nextLine().toUpperCase();

		return name;	
	}

	/**
	 * Method that displays the wins and money of the given user name
	 * @param playerOfName - Name of the player that the user provides
	 */
	public void findPlayerMenu(Player playerOfName) {
		System.out.println("\n\t\t      - PLAYER INFO -");

		repeatTable("=", 18, 3);
		System.out.printf("|%-18s|%-18s|%-18s|\n" ,"NAME", "# WINS", "BALANCE");
		
		repeatTable("=", 18, 3);
		System.out.printf("|%-18s|%-18s|%-18s|\n" , playerOfName.getName(), playerOfName.getWins(), playerOfName.getMoney() + " $");
		repeatTable("-", 18, 3);

		System.out.print("\n\nPress \"Enter\" to continue... ");
		
		while (!keyboard.nextLine().equals("")) {
			System.out.println("\nInvalid input, must press enter to return to main menu\n");
			System.out.print("Press \"Enter\" to continue... ");
		}
	}

	/**
	 * Method that displays to the user that no player exists from the given user name
	 */
	public void displayNoPlayer() {
		System.out.print("\nName does not exist in database. ");
	}

	/**
	 * Method that displays the leaderboard for the top player(s)
	 * @param topList - ArrayList of Player objects
	 */
	public void topPlayerMenu(ArrayList<Player> topList) {
		System.out.println("\n\t    - TOP PLAYERS -");

		repeatTable("=", 18, 2);
		System.out.printf("|%-18s|%-18s|\n" ,"NAME", "# WINS");
		repeatTable("=", 18, 2);
		
		for (Player player : topList) {
			System.out.printf("|%-18s|%-18s|\n" , player.getName(), player.getWins());
			repeatTable("-", 18, 2);
		}

		System.out.print("\n\nPress \"Enter\" to continue... ");
		
		while (!keyboard.nextLine().equals("")) {
			System.out.println("\nInvalid input; must press enter to continue to main menu.\n");
			System.out.print("Press \"Enter\" to continue... ");
		}	
	}
	
	/**
	 * Simple method that lets the user know that data is being saved
	 */
	public void savePrompt() {
		System.out.println("Saving...\nDone! Please visit use again!");
		keyboard.close(); // Close the scanner at the very end of the program
	}
}
