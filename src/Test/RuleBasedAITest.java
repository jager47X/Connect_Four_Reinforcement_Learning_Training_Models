package Test;

import Connect4.Connect4;
import Connect4.RuleBasedAI;
import Connect4.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleBasedAITest {
//colum index 0-6 associate with player input 1-7
    @Test
    void testIsValidMove() {
        // You may need to adjust the test logic based on the actual implementation of isValidMove
        Board board = new Board(7, 6, Connect4.EMPTY, Connect4.PLAYER1, Connect4.PLAYER2);
        assertTrue(RuleBasedAI.isValidMove(board, 0));  // Assuming column 0 is a valid move
        assertFalse(RuleBasedAI.isValidMove(board, 8)); // Assuming column 8 is an invalid move
    }

    @Test
    void testFindAvailableRow() {
        // You may need to adjust the test logic based on the actual implementation of findAvailableRow
        Board board = new Board(7, 6, Connect4.EMPTY, Connect4.PLAYER1, Connect4.PLAYER2);
        assertEquals(5, RuleBasedAI.findAvailableRow(board, 0)); // Assuming column 0 has an available row at index 5
        board.setTileValue(5,5,Connect4.PLAYER1);
        board.setTileValue(4,5,Connect4.PLAYER1);
        board.setTileValue(3,5,Connect4.PLAYER1);
        board.setTileValue(2,5,Connect4.PLAYER1);
        board.setTileValue(1,5,Connect4.PLAYER1);
        board.setTileValue(0,5,Connect4.PLAYER1);
        assertEquals(-1, RuleBasedAI.findAvailableRow(board, 5)); // Assuming column 6 is full and returns -1
    }

    @Test
    void testMakeMove() {
        // You may need to adjust the test logic based on the actual implementation of makeMove
        Board board = new Board(7, 6, Connect4.EMPTY, Connect4.PLAYER1, Connect4.PLAYER2);
        char activePlayer = Connect4.PLAYER1;
        char opponent = Connect4.PLAYER2;

        // Assuming some setup to make a winning move
        // ...

        // Call makeMove and make assertions
        int move = RuleBasedAI.makeMove(board, activePlayer, opponent);

    }
}
