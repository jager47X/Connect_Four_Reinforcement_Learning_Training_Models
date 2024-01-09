package ReinforceLearning;

import dto.Connect4Dto;

import java.io.*;
import java.util.Random;
interface QLearningAgent {
    void trainAgent(int episodes);
    void testAgent();
    void resetBoard();//connect4
    int[] makeMove(int action);
    int calculateReward();
    boolean isGameOver();//connect4
    void printBoard();//connect4
    int[] getLegalActions();
    int stateToIndex(int[][] state);
    int selectAction();
    void saveQTableToCSV(Connect4Dto dto);//connect4 dto
    void loadQTableFromCSV(Connect4Dto dto);//connect4 dto
}
public abstract class AbstractQLearningAgent implements QLearningAgent {
    // Constants
    static final int ROWS = 6;
    static final int COLS = 7;
    static final int ACTIONS = 7;

    // Q-table
    double[][] qTable = new double[ROWS * COLS][ACTIONS];

    // Hyperparameters
    double learningRate = 0.1;
    double discountFactor = 0.9;
    double explorationRate = 0.8;
    double minExplorationRate = 0.1;
    double explorationDecay = 0.95;

    // Environment-specific variables
    int[][] board;//connect4
    int currentPlayer;//connect4

    // Action selection logic
    @Override
    public int selectAction() {
        if (Math.random() < explorationRate) {
            // Exploration: choose a random action
            int[] legalActions = getLegalActions();
            return legalActions[new Random().nextInt(legalActions.length)];
        } else {
            // Exploitation: choose the action with the highest Q-value
            int stateIndex = stateToIndex(board);
            double[] qValues = qTable[stateIndex];
            int[] legalActions = getLegalActions();

            int bestAction = legalActions[0];
            double bestQValue = qValues[bestAction];

            for (int action : legalActions) {
                if (qValues[action] > bestQValue) {
                    bestAction = action;
                    bestQValue = qValues[action];
                }
            }

            return bestAction;
        }
    }

    // Additional methods and implementations go here

    @Override
    public void saveQTableToCSV(Connect4Dto dto) {
        dto.exportCSV();
    }

    // Load Q-table from CSV file
    @Override
    public void loadQTableFromCSV(Connect4Dto dto) {
        dto.import_CSV();
    }
    @Override
    public void trainAgent(int episodes) {
        // Implement training logic
    }

    @Override
    public void testAgent() {
        // Implement testing logic
    }
}