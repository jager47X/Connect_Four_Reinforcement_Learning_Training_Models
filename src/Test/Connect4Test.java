package Test;

import Connect4.Connect4;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Connect4Test {

    @Test
    void testPlayerDrop() {
        Connect4 game = new Connect4();

        // Test a valid drop
        assertTrue(game.playerDrop(4));

        // Test an invalid drop (outside column bounds)
        assertFalse(game.playerDrop(8));

        // Test an invalid drop (column full)
        assertTrue(game.playerDrop(4)); // Player 2
        assertTrue(game.playerDrop(4)); // Player 1
        assertTrue(game.playerDrop(4)); // Player 2
        assertTrue(game.playerDrop(4)); // Player 1
        assertTrue(game.playerDrop(4)); // Player 2
        assertFalse(game.playerDrop(4)); // Player 1
        assertTrue(game.playerDrop(5)); // Column full for this test case
    }

    @Test
    void testWinCheck() {
        Connect4 game = new Connect4();

        // Test for a win
        assertTrue(game.playerDrop(1));
        assertTrue(game.playerDrop(2));
        assertTrue(game.playerDrop(1));
        assertTrue(game.playerDrop(2));
        assertTrue(game.playerDrop(1));
        assertTrue(game.playerDrop(2));
        assertTrue(game.playerDrop(1)); // Player 1 wins
        assertTrue(game.winCheck());
        assertEquals(1, game.getWinner());

        // Test for a draw
        assertTrue(game.playerDrop(3));
        assertTrue(game.playerDrop(4));
        assertTrue(game.playerDrop(5));
        assertTrue(game.playerDrop(6));
        assertTrue(game.playerDrop(7));
        assertTrue(game.playerDrop(1));
        assertTrue(game.playerDrop(2));
        assertTrue(game.playerDrop(3));
        assertTrue(game.playerDrop(4));
        assertTrue(game.playerDrop(5));
        assertTrue(game.playerDrop(6));
        assertTrue(game.playerDrop(7));
        assertTrue(game.playerDrop(1));
        assertTrue(game.playerDrop(2));
        assertTrue(game.playerDrop(3));
        assertTrue(game.playerDrop(4));
        assertTrue(game.playerDrop(5));
        assertTrue(game.playerDrop(6));
        assertTrue(game.playerDrop(7));
        assertTrue(game.winCheck()); // No winner (draw)
        assertEquals(1, game.getWinner());
    }

    // Add more test cases as needed
}
