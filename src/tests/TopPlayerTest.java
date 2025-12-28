package tests;

import application.GameManager;
import saveableObjects.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.*;

public class TopPlayerTest {
    
    @Test
    public void testOneTopPlayer() throws FileNotFoundException, IOException {
        GameManager gameManager = new GameManager();        

        gameManager.getPlayerList().add(new Player("Norris", 0f, 30));
    
        ArrayList<Player> resultList = gameManager.findTopPlayer();

        // Test and see if one player is displayed on the top player menu
        Assert.assertEquals(1, resultList.size());
    }

    @Test
    public void testThreeTopPlayers() throws FileNotFoundException, IOException {
        GameManager gameManager = new GameManager();

        gameManager.getPlayerList().add(new Player("Norris", 0f, 18));

        ArrayList<Player> resultList = gameManager.findTopPlayer();

        // Test and see if three players are displayed on the top player menu
        Assert.assertEquals(3, resultList.size());
    }
}
