package ReinforceLearning;

import dto.Connect4Dto;

import java.util.Arrays;
import java.util.Random;

interface ReinforceLearningAgent2D {
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
public abstract class AbstractReinforceLearningAgent2D implements ReinforceLearningAgent2D {
    static int ROWS;
    static int COLS ;
    static int ACTIONS;

    protected AbstractReinforceLearningAgent2D(int rows, int cols, int action, int[][] environment, double[][]qTable) {
        ROWS=rows;
        COLS=cols;
        ACTIONS=action;
        Environment= Arrays.copyOf(environment, environment.length);
        QTable=Arrays.copyOf(qTable,qTable.length);
    }

    // Q-table
    double[][] QTable = new double[ROWS * COLS][ACTIONS];
    // Environment-specific variables
    int[][] Environment;

    // Hyperparameters
    double learningRate = 0.1;
    double discountFactor = 0.9;
    double explorationRate = 0.8;
    double minExplorationRate = 0.1;
    double explorationDecay = 0.95;


    // Action selection logic

    @Override
    public int selectAction() {
        if (Math.random() < explorationRate) {
            // Exploration: choose a random action
            int[] legalActions = getLegalActions();//place chip by AI on the available columns
            return legalActions[new Random().nextInt(legalActions.length)];
        } else {
            // Exploitation: choose the action with the highest Q-value

            int stateIndex = stateToIndex(Environment);// converting the current state of the environment into an index

            double[] qValues = QTable[stateIndex];//retrieving the Q-values associated with the current state from the Q-table.
            int[] legalActions = getLegalActions();//place chip by AI on the available columns

            int bestAction = legalActions[0];//initialize bestAction by taking the head of legalAction
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


}

