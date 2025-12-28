package tests;

import gameComponents.BlackJackGame;
import gameComponents.Card;
import gameComponents.CardDeck;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.*;

public class BlackJackGameTest {

    private CardDeck cardDeck = new CardDeck(); // Card deck to use for the tests
    
    @Test
    public void testNatural() throws FileNotFoundException, IOException {

        BlackJackGame blackJack = new BlackJackGame(cardDeck);

        blackJack.setPlayerScore(21);
        
        blackJack.getDealerHand().set(0, new Card(5, "spade"));
        blackJack.setDealerScore();
        
        String result = blackJack.getWinner();
        // Test and see if getting a natural works
        Assert.assertEquals("natural", result);
    }

    @Test
    public void testNaturalTie() throws FileNotFoundException, IOException {

        BlackJackGame blackJack = new BlackJackGame(cardDeck);

        blackJack.setPlayerScore(21);
        
        blackJack.getDealerHand().set(0, new Card(1, "spade"));
        blackJack.getDealerHand().add(new Card(10, "spade"));

        String result = blackJack.getWinner();

        // Test and see if a it returns a tie when both the player and dealer have a natural
        Assert.assertEquals("tie", result);
    }

    @Test
    public void testNormalTie() {

        BlackJackGame blackJack = new BlackJackGame(cardDeck);

        blackJack.setPlayerScore(10);
        
        blackJack.getDealerHand().set(0, new Card(5, "spade"));
        blackJack.getDealerHand().add(new Card(5, "spade"));
        blackJack.setDealerScore(); // Dealer's score should equal 10

        String result = blackJack.getWinner();
        // Test and see if a it returns a tie
        Assert.assertEquals("tie", result);
    }

    @Test
    public void testMultipleAces() {
        BlackJackGame blackJack = new BlackJackGame(cardDeck);
        // Add three aces, then a 10 card
        blackJack.getPlayerHand().set(0, new Card(1, "spade"));
        blackJack.getPlayerHand().set(1, new Card(1, "spade"));
        blackJack.getPlayerHand().add(new Card(1, "spade"));
        blackJack.getPlayerHand().add(new Card(10, "spade"));

        int score = blackJack.calculateScore(blackJack.getPlayerHand());

        Assert.assertEquals(13, score); // Test to see that aces are reverted to 1  
    }
}
