package ReinforceLearning.Connect4Handler;

import ReinforceLearning.AbstractReinforceLearningAgentOn2D;
import dto.Connect4Dto;
import target.Connect4;

import java.util.ArrayList;
import java.util.List;


public class ConnectFourQLearningAgent extends AbstractReinforceLearningAgentOn2D {

    int currentPlayer;//connect4
    Connect4 currentState;

    Connect4Dto dto;
    public ConnectFourQLearningAgent(int row, int col, int action, Connect4Dto dto) {
        super(row,col,action);
        currentPlayer = (dto.getBoard().getActivePlayer() == dto.getBoard().PLAYER1) ? 1: 2;
        currentState=dto.getBoard();
    }
    // Additional Connect Four-specific methods and logic go here

    // Implement interface methods

    @Override
    public void trainAgent(int episodes) {

    }

    @Override
    public void testAgent() {

    }

    @Override
    public void resetBoard() {
        // Implement resetBoard for Connect Four
    }


    @Override
    public int[] makeMove(int action) {
        // Implement makeMove for Connect Four
        return null;
    }

    @Override
    public int calculateReward() {
        // Implement calculateReward for Connect Four
        return 0;
    }

    @Override
    public boolean isGameOver() {
        // Implement isGameOver for Connect Four
        return false;
    }

    @Override
    public void printBoard() {
        // Implement printBoard for Connect Four
    }

    @Override
    public int[] getLegalActions() {
        List<Integer> legalActionsList = new ArrayList<>();
// Iterate over each column to check if it's a legal action
            for (int col = 0; col < currentState.getCOLS_SIZE(); col++) {
                if (currentState.isValidColumn(col)) {
                    legalActionsList.add(col);
                }
            }

        // Convert the list of legal actions to an array
        int[] legalActions = new int[legalActionsList.size()];
        for (int i = 0; i < legalActions.length; i++) {
            legalActions[i] = legalActionsList.get(i);
        }


        return legalActions;
    }

    @Override
    public int stateToIndex(int[][] state) {
        return 0;
    }


    @Override
    public int selectAction() {
        return super.selectAction();
    }

    @Override
    public void saveQTableToCSV(Connect4Dto dto) {

    }

    @Override
    public void loadQTableFromCSV(Connect4Dto dto) {

    }
    // Additional Connect Four-specific methods and logic go here
}
