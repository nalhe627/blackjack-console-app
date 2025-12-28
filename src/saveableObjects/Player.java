package saveableObjects;

public class Player {
	
	/**
	 * This class represent a player, as recorded in the Database
	 */

	// Attributes of player
	 private String name;
	 private float money;
	 private int wins;


	/**
	 * Constructor for the Player class
	 * @param name - Name of the player
	 * @param money - Money the player has in possession
	 * @param wins - Amount of wins the player has
	 */
	public Player(String name, float money, int wins) {
		this.name = name;
		this.money = money;
		this.wins = wins;
	}

	/**
	 * Alternate constructor for when the user does not have saved data
	 * @param name - Name of the player
	 */
	public Player(String name) {
			this.name = name;
			this.money = 100f;
			this.wins = 0;
	}

	/**
	 * Getter method for the name attribute
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter method for the money attribute
	 * @return
	 */
	public float getMoney() {
		return money;
	}

	/**
	 * Getter method for the wins attribute
	 * @return
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * Adds money to the player's balance
	 * @param money - Amount of money to add to player's balance
	 */
	public void addMoney(float money) {
		this.money += money;
	}

	/**
	 * Subtracts money from the player's balance
	 * @param money - Amount of money to subtract
	 */
	public void loseMoney(float money) {
		this.money -= money;
	}

	/**
	 * Adds a win to the player's win attribute
	 */
	public void addWin() {
		this.wins += 1;
	}

	/**
	 * toString method for the player object
	 */
	public String toString() {
		return name;
	}
}
