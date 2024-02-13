package ReinforceLearning;



import dto.Connect4Dto;
import dto.QTableDto;
import GameEnviroment.Connect4;
import GameEnviroment.RuleBasedAI;

import java.util.*;


public class  ReinforceLearningAgentConnectFour extends AbstractReinforceLearningAgent2D {
    int TotalReward;

    public ReinforceLearningAgentConnectFour (Connect4Dto connect4dto,QTableDto importedQTable) {
        super(connect4dto,importedQTable);
        TotalReward=0;
    }

    public ReinforceLearningAgentConnectFour (Connect4Dto connect4dto) {
        super(connect4dto);
        TotalReward=0;
    }

    public QTableDto SupervisedLearning() {//use multi-thread //if episodes=1 then vs human if not AI vs AI

        QTableDto episode=new QTableDto(QTable);
        Environment.setActivePlayer(Connect4.PLAYER2);
        Environment.setTurn(0);


        do {
            if(isTerminateState(Environment)){
                Environment.setWinner(0);
                break;
            }
            //update environment/state
            Environment = connect4Dto.getGame();

            switchPlayer();
            while(!Environment.playerDrop(RuleBasedAI.makeMove(Environment.getBoard(),Environment.getActivePlayer(),Environment.getNonActivePlayer()))) {//update move
                Environment.playerDrop(RuleBasedAI.makeMove(Environment.getBoard(),Environment.getActivePlayer(),Environment.getNonActivePlayer()));
            }

            // calculateReward(board.getGame(),);
            if (Environment.winCheck()||Environment.getWinner()==0) {//check winner 1 or 2
                episode.addLine();
                break;
            }else{
                episode.addLine();
            }
        }while(!isTerminateState(Environment));


    return QTable.converge(episode);

    }
    private void switchPlayer(){
        if (Environment.getActivePlayer() == Connect4.PLAYER1) {//switch player
            Environment.setActivePlayer(Connect4.PLAYER2);
            Environment.setNonActivePlayer(Connect4.PLAYER1);
        } else {
            Environment.setActivePlayer(Connect4.PLAYER1);
            Environment.setNonActivePlayer(Connect4.PLAYER2);
        }

    }

    public QTableDto ReinforceLearning() {
        QTableDto episode = new QTableDto(QTable);
        Environment.setActivePlayer(Connect4.PLAYER1);
        Environment.setTurn(0);


        do {
            if (isTerminateState(Environment)) {
                Environment.setWinner(0);
                break;
            }
            // update environment/state
            switchPlayer();

            Environment.playerDrop(selectAction());
            StringBuilder stateString = getState();
            String state=stateString.toString();

            int currentTurn = connect4Dto.getGame().getCurrentTurn();
            String nextState = QTable.getNextState(currentTurn, state);

             QTable.hasNextState=!Objects.equals(nextState, "NULL");
            if (QTable.hasNextState) {
                int action = Environment.getLocation(Environment.getCurrentTurn());
                double reward=0.0;
                if(Environment.getActivePlayer()==Connect4.PLAYER1){//fliped
                     reward= Environment.getTotalRewardP2();
                }else {
                    reward = Environment.getTotalRewardP1();
                }
                updateQValue(state, action, reward, nextState);//update to RL Qtable
            }

            Environment.displayBoard();
            if (Environment.winCheck() || Environment.getWinner() == 0) {// check winner 1 or 2
                System.out.print("saving into qtableDto:");
                episode.addLine();
                break;
            } else {
                System.out.print("saving into qtableDto:");
                episode.addLine();
            }
        } while (!isTerminateState(Environment));


        return QTable.converge(episode);
    }

    private boolean isTerminateState(Connect4 game) {
        return game.getWinner() != -1;
    }


}
