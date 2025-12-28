package gameComponents;

import java.util.ArrayList;

public class BlackJackGame {
    
	/**
	* In this class you implement the game
	* You should use CardDeck class here
	* See the instructions for the game rules
	*/

	// Attributes
	private CardDeck cardDeck;
	private ArrayList<Card> playerHand = new ArrayList<>();
	private ArrayList<Card> dealerHand = new ArrayList<>();
	private int playerScore; // Score of the player
	private int dealerScore; // Score of the dealer

	/**
	 * Constructor for the blackjack game class.
	 * Adds 2 cards to the player's hand and 1 card to the dealer's hand, then calculates both of their scores
	 */
	public BlackJackGame(CardDeck cardDeck) {
		this.cardDeck = cardDeck;
		
		checkCardDeck();
		playerHand.add(cardDeck.dealCard());
		checkCardDeck();
		playerHand.add(cardDeck.dealCard());
		this.playerScore = calculateScore(playerHand);

		checkCardDeck();
		dealerHand.add(cardDeck.dealCard());
		this.dealerScore = calculateScore(dealerHand);
	}

	/**
	 * Method that checks if the cardDeck is empty and refills if it is
	 */
	public void checkCardDeck() {
		if (cardDeck.isEmpty()) {
			cardDeck.reset();
		}
	}

	/**
	 * Method that runs when the user chooses to hit in blackjack.
	 * Adds a card to the player's deck, then it recalculates their score
	 */
	public void hit() {
		checkCardDeck();
		playerHand.add(cardDeck.dealCard());
		this.playerScore = calculateScore(playerHand);
	}

	/**
	 * Method that runs when the user chooses to stand in blackjack
	 */
	public void stand() {
		do {
			checkCardDeck();
			dealerHand.add(cardDeck.dealCard());
		} while (calculateScore(dealerHand) <= 16); // Dealer keeps hitting until score is 17 or more, in which case they stand
		
		this.dealerScore = calculateScore(dealerHand); // Assign dealers score to the attribute
	}

	/**
	 * Method that calculates the score of either the dealer or player's hand
	 * @param someonesHand - ArrayList of Cards in the persons's hand
	 * @return Score of the person
	 */
	public int calculateScore(ArrayList<Card> someonesHand) {
		int score = 0;
		boolean ace = false; // Keep track if an ace is worth 11
		for (Card card : someonesHand) {
			if (card.getRank() == 1) { // Rank 1 is an ace
				if (score + 11 > 21) {
					score++; // Ace = 1
				}
				else { // Ace = 11
					score += 11;
					ace = true;
				}
			}
			else {
				score += card.getRank() > 10 ? 10 : card.getRank(); // If the card rank is more than 10, set it to 10
			}
		}
		return score -= score > 21 && ace ? 10 : 0; // Recalculate score if there's an ace worth 11, then return the score
	}

	/**
	 * Calculates if someone has one the game. Returns none if no one has won yet
	 * @return String of who won (or none)
	 */
	public String getWinner() {

		if (playerScore == 21 && playerHand.size() == 2) { // Player has a natural
			// Add one more card into dealer's hand and see if they also have a natural

			if (dealerHand.size() == 1) { // The if statement is for a unit test; just imagine it not being there
				checkCardDeck();
				dealerHand.add(cardDeck.dealCard());
			}
			this.dealerScore = calculateScore(dealerHand);
			
			// Tie game if both have a natural, otherwise player wins with a natural
			return dealerScore == 21 ? "tie" : "natural";
		}
		// Check if the player goes over 21 when hitting
		else if (playerScore > 21) {
			return "dealer";
		}
		// Make sure dealer's hand is more than one (player has standed)
		else if (dealerHand.size() > 1) {
			if (dealerScore > playerScore && dealerScore <= 21) { // Normal lose situation
				return "dealer";
			}
			else if (dealerScore > 21 || playerScore > dealerScore) { // Normal win situation
				return "player";
			}
			else if (playerScore == dealerScore) { // Normal tie situation
				return "tie";
			}
		}
		
		return "none"; // Return none if no one has won yet
	}

	/**
	 * Getter method for playerhand attribute
	 * @return ArrayList of Cards in the player's hand
	 */
	public ArrayList<Card> getPlayerHand() {
		return playerHand;
	}

	/**
	 * Getter method for dealerhand attribute
	 * @return ArrayList of Cards in the dealer's hand
	 */
	public ArrayList<Card> getDealerHand() {
		return dealerHand;
	}
	
	/**
	 * Getter method for the playerScore attribute
	 * @return The score of the player
	 */
	public int getPlayerScore() {
		return playerScore;
	}

	/**
	 * Getter method for the dealerScore attribute
	 * @return The score of the dealer
	 */
	public int getDealerScore() {
		return dealerScore;
	}

	/**
	 * Setter method for playerScore attribute. Specifically used for unit testing
	 * @param playerScore - number to change player's score to
	 */
	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	/**
	 * Setter method for dealerScore attribute. Also used for unit testing
	 */
	public void setDealerScore() {
		this.dealerScore = calculateScore(dealerHand);
	}
}
