package ReinforceLearning;

import dao.QEntry;
import dao.QTableDao;
import dto.Connect4Dto;
import dto.QTableDto;
import target.Connect4;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

interface ReinforceLearningAgent2D{
    QTableDto SupervisedLeanrning();
    QTableDto ReinforceLearning();
    int[] getLegalActions();
    String stateToIndex(Connect4Dto state);
    int selectAction();



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
    double explorationRate = 0.1;
    double minExplorationRate = 0.1;
    double explorationDecay = 0.95;


    // Action selection logic

    @Override
    public int selectAction() {
        if (Math.random() < explorationRate) {
            // Exploration: choose a random action
            System.out.println("using random action");
            int[] legalActions = getLegalActions();//place chip by AI on the available columns
            return legalActions[new Random().nextInt(legalActions.length)];
        } else {
            // Exploitation: choose the action with the highest Q-value
            String stateIndex = stateToIndex(connect4Dto);// converting the current state of the environment into an index
            System.out.println(
                    "\naction 1 reward :"+getQTable().getQvalue(stateIndex).getReward(1)+
                    "\naction 2 reward :"+getQTable().getQvalue(stateIndex).getReward(2)+
                    "\naction 3 reward :"+getQTable().getQvalue(stateIndex).getReward(3)+
                    "\naction 4 reward :"+getQTable().getQvalue(stateIndex).getReward(4)+
                    "\naction 5 reward :"+getQTable().getQvalue(stateIndex).getReward(5)+
                    "\naction 6 reward :"+getQTable().getQvalue(stateIndex).getReward(6)+
                    "\naction 7 reward :"+getQTable().getQvalue(stateIndex).getReward(7));
            int[] legalActions = getLegalActions();//place chip by AI on the available columns
            QEntry qValues= QTable.getQvalue(stateIndex);//retrieving the Q-values associated with the current state from the Q-table.

            return findBestAction(legalActions,qValues);//find the best action and return it
        }
    }

    private int findBestAction(int[] legalActions,  QEntry  qValues){

        int bestAction = legalActions[0];//initialize bestAction by taking the head of legalAction
        double bestQValue = qValues.getReward(bestAction);


        for (int action : legalActions) {///find the best
            if (qValues.getReward(action) > bestQValue) {
                bestAction = action;
                bestQValue = qValues.getReward(action);
            }
        }
        if(bestQValue==-1){
            System.out.println("Reward is -1 returning random choice");
            return legalActions[new Random().nextInt(legalActions.length)];
        }
        System.out.println("returning the bestaction:"+bestAction);
        return bestAction;
    }


}

