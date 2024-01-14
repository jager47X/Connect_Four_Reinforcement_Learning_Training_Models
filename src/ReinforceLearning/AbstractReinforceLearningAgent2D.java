package ReinforceLearning;

import dao.QTableDao;
import dto.Connect4Dto;
import dto.QTableDto;
import target.Board;
import target.Connect4;

import java.util.Random;

interface ReinforceLearningAgent2D{
    void trainAgent(boolean humanInteraction);
    void testAgent(boolean humanInteraction);
    void trainAgent();
    void testAgent();
    boolean makeMove(int action);
    void calculateReward(Board board, int row, int col, char activePlayer);
    int[] getLegalActions();
    String stateToIndex(Connect4Dto state);
    int selectAction();


    void saveQTableToCSV(QTableDto dto);//connect4 dto
    void loadQTableFromCSV(QTableDao dao);//connect4 dto


}
public abstract class AbstractReinforceLearningAgent2D implements ReinforceLearningAgent2D{
    protected QTableDao QTable;
    // Environment-specific variables
    protected Connect4 Environment;
    protected Connect4Dto connect4Dto;
    static int ROWS;
    static int COLS;
    static int ACTIONS;

    public QTableDao getQTable() {
        return QTable;
    }

    public void setQTable(QTableDao QTable) {
        this.QTable = QTable;
    }

    public Connect4 getEnvironment() {
        return Environment;
    }

    public void setEnvironment(Connect4 environment) {
        Environment = environment;
    }

    protected AbstractReinforceLearningAgent2D(Connect4Dto connect4Dto) {
        ROWS=connect4Dto.getGame().getROWS_SIZE();
        COLS=connect4Dto.getGame().getCOLS_SIZE();
        ACTIONS=connect4Dto.getGame().getCOLS_SIZE();
        this.connect4Dto=connect4Dto;
        this.QTable= QTableDao.getInstance();
        Environment=connect4Dto.getGame();
    }


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
            Connect4Dto dto=new Connect4Dto(Environment);
            String stateIndex = stateToIndex(dto);// converting the current state of the environment into an index

            int[] qValues = QTable.get(stateIndex);//retrieving the Q-values associated with the current state from the Q-table.
            int[] legalActions = getLegalActions();//place chip by AI on the available columns

            return findBestAction(legalActions,qValues);//find the best action and return it
        }
    }

    private int findBestAction(int[] legalActions, int[] qValues){
        int bestAction = legalActions[0];//initialize bestAction by taking the head of legalAction
        double bestQValue = qValues[bestAction];

        for (int action : legalActions) {///find the best
            if (qValues[action] > bestQValue) {
                bestAction = action;
                bestQValue = qValues[action];
            }
        }
        return bestAction;
    }



}

