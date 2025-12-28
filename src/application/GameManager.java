package application;

import saveableObjects.Player;
import userInterface.Menu;
import gameComponents.BlackJackGame;
import gameComponents.CardDeck;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;

public class GameManager {
	
	/* This class handles the list of players, and handles the flow of information between the other classes
	 * It will probably need at least the following methods:
	 * 		A method to load the player data in the txt file into an arraylist of Player objects
	 * 		A save method to store the arraylist of Players into the the txt file
	 * 		A method to search for a player based their name
	 * 		A method to find the top player(s)
	 * Depending on your design, you may need and can add more methods
	 */
	
	// Attributes
	private ArrayList<Player> playerList = new ArrayList<>(); // Create the ArrayList first, players are appended later
	private Menu menu = new Menu();
	private BlackJackGame blackJack; // Represents a single game of blackjack
	private Player player; // Represents a player object of the current user

	/**
	 * Main constructor of GameManager that loads the players' data and appends it to the playerList attribute
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	 public GameManager() throws FileNotFoundException, IOException {
		File file = new File("res/casinoInfo.txt");
		Scanner playerFile = new Scanner(file);

		while (playerFile.hasNext()) {
			String line = playerFile.nextLine();
			String[] playerInfo = line.split(","); // Split info of player into an array

			Player player = new Player(playerInfo[0], Float.parseFloat(playerInfo[1]), Integer.parseInt(playerInfo[2]));
			playerList.add(player);
		}
		playerFile.close();	
	 }

	/**
	 * Method that saves the user's data into the casinoInfo.txt file
	 * @throws IOException
	 */
	public void save() throws IOException {
		PrintWriter saveFile = new PrintWriter("res/casinoInfo.txt");
		for (Player player : playerList) {
			saveFile.println(player.getName() + ',' + player.getMoney() + ',' + player.getWins());
		}

		menu.savePrompt();
		saveFile.close();
	}

	/**
	 * Method that finds the player in the ArrayList of players based on the provided name.
	 * If no player is found, method invokes a menu method that displays to the user that the player does not exist
	 * @param name - String of the player's name that the user provides
	 */
	public void findPlayer(String name) {
		boolean inDataBase = false; // Boolean that indicates whether the user's name is in database or not

		for (Player player : playerList) {
			if (name.equalsIgnoreCase(player.getName())) {
				inDataBase = true;
				menu.findPlayerMenu(player);
				break;
			}
		}	

		if (!inDataBase) {
			menu.displayNoPlayer();
			menu.returnMenu();
		}
	}

	/**
	 * Method that iterates through each player of the ArrayList and adds the player(s) with the highest win to another array
	 * @return ArrayList of the player(s) with the highest wins
	 */
	public ArrayList<Player> findTopPlayer() {
		ArrayList<Player> topList = new ArrayList<>(); // Possible that there are more than one top player
		int highestWins = 0; // Default variable for highest wins to zero

		for (Player player : playerList) {
			if (player.getWins() == highestWins) {
				topList.add(player);
			}
			else if (player.getWins() > highestWins) {
				highestWins = player.getWins();
				topList.clear();
				topList.add(player);
			}
		}
		return topList;
	}

	/**
	 * Method that checks to see if the user is a new player or not
	 * @param name - String representing the user's name
	 * @return Boolean telling the menu whether the user is an existing player
	 */
	public boolean isNewPlayer(String name) {
		boolean newPlayer = true; // Flag that indicates whether the user has existing data or not; Defaulted to false
		this.player = new Player(name); // Assign attribute to a new player

		for (Player playerInArray : playerList) {
			if (playerInArray.getName().equalsIgnoreCase(name)) {
				newPlayer = false;
				this.player = playerInArray; // Change it so that the variable equals the player in the array
				break; // Normally wouldn't use this, but it's possible that the list is long
			}
		}
		if (newPlayer) {
			playerList.add(player); // If they're new, add them to the ArrayList
		}
		
		return newPlayer;
	}

	/**
	 * Method that starts the whole program
	 * @throws IOException
	 */
	public void start() throws IOException {
		String mainMenuChoice = menu.startMenu();
		// Loop starts/continues if player doesn't choose to exit
		while (!mainMenuChoice.equals("e")) {
			if (mainMenuChoice.equals("p")) { // User chooses to play
				boolean newPlayer = isNewPlayer(menu.startGameMenu()); // Get name of player, then find them in database
				menu.welcomePrompt(newPlayer, player);
				
				CardDeck cardDeck = new CardDeck(); // Create one card deck and use that for every blackjack game
				String newGame; // Initialize variable for loop
				do {
					if (player.getMoney() != 0) { // New round of blackjack starts if player isn't broke
						blackJack = new BlackJackGame(cardDeck);
						float bet = menu.getBetFromPlayer(player.getMoney());
						
						String hitOrStand = ""; // Make it equal to an empty string to make loop work
						String winner = blackJack.getWinner(); // Check winner status (player might have a natural)
						menu.blackJackTable(blackJack.getPlayerHand(), blackJack.getDealerHand());
	
						// Get choice between hit or stand if player doesn't have a natural
						if (winner.equals("none")) {
							hitOrStand = menu.yesOrNo("1", "2", "\nSelect an option:\n\n" +
							"\t1.Hit\n" +
							"\t2.Stand\n");
						}
						
						// User chooses to hit (will loop until player exceeds 21 or chooses to stand)
						while (hitOrStand.equals("1") && winner.equals("none")) {
							blackJack.hit();
							winner = blackJack.getWinner();
							menu.blackJackTable(blackJack.getPlayerHand(), blackJack.getDealerHand());
							
							if (winner.equals("none")) { // Check winner status again
								hitOrStand = menu.yesOrNo("1", "2", "\nSelect an option:\n\n" +
								"\t1.Hit\n" +
								"\t2.Stand\n");
							}
						}
	
						if (hitOrStand.equals("2")){ // User chooses to stand
							blackJack.stand();
							menu.blackJackTable(blackJack.getPlayerHand(), blackJack.getDealerHand());
							winner = blackJack.getWinner();
						}
	
						// Check who won (if it's a tie, switch-case is skipped)
						switch (winner) {
							case "natural": // Same as player case except bet is multiplied by 1.5
								bet *= 1.5;
							case "player":
								// Add a win to player stats and increase money
								player.addWin();
								player.addMoney(bet);
								break;
							case "dealer":
								player.loseMoney(bet); // Player loses money
								break;
						}
						// Display winner and get input from use whether to play again
						newGame = menu.displayResult(winner, bet);
					}
					else { // Player is broke, return them to main menu
						menu.displayNoMoney();
						newGame = "n"; // Make it equal to "n" in order to exit the loop
					}
				} while (newGame.equals("y"));
				menu.returnMenu(); // Display that player is being returned to main menu
			}
			else if (mainMenuChoice.equals("s")) { // User chooses to search something
				String searchMenuChoice = menu.searchMenu();
	
				if (searchMenuChoice.equals("t")) { // User wants to see top player(s)
					menu.topPlayerMenu(findTopPlayer());
				}
				else if (searchMenuChoice.equals("n")) { // User wants to look at a specific player
					findPlayer(menu.findPlayerInput());
				}
			}

			mainMenuChoice = menu.startMenu(); // When user is done with game or search menu, return to main menu
		}
		// User exits the main menu, time to save
		save();
	}

	/**
	 * Getter method of the playerList attribute
	 * @return ArrayList of players
	 */
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}
}
