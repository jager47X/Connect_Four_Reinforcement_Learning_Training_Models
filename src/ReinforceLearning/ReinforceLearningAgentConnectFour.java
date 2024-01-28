package ReinforceLearning;



import dto.Connect4Dto;
import dto.QTableDto;
import target.Connect4;
import target.RuleBasedAI;

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

        QTableDto episode=new QTableDto(Environment);
        Environment.setActivePlayer(Connect4.PLAYER2);
        Environment.setTurn(0);


        do {
            if(isTerminateState(Environment)){
                Environment.setWinner(0);
                break;
            }
            //update environment/state
            Environment = connect4Dto.getGame();

            if (Environment.getActivePlayer() == Connect4.PLAYER1) {//switch player
                Environment.setActivePlayer(Connect4.PLAYER2);
                Environment.setNonActivePlayer(Connect4.PLAYER1);
            } else {
                Environment.setActivePlayer(Connect4.PLAYER1);
                Environment.setNonActivePlayer(Connect4.PLAYER2);
            }
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


    public QTableDto ReinforceLearning() {
        QTableDto episode = new QTableDto(Environment);
        Environment.setActivePlayer(Connect4.PLAYER1);
        Environment.setTurn(0);


        do {
            if (isTerminateState(Environment)) {
                Environment.setWinner(0);
                break;
            }
            // update environment/state
            System.out.print("Player");
            if (Environment.getActivePlayer() == Connect4.PLAYER1) {// switch player
                Environment.setActivePlayer(Connect4.PLAYER2);
                Environment.setNonActivePlayer(Connect4.PLAYER1);
                System.out.println("1's turn");
            } else {
                Environment.setActivePlayer(Connect4.PLAYER1);
                Environment.setNonActivePlayer(Connect4.PLAYER2);
                System.out.println("2's turn");
            }

            Environment.playerDrop(selectAction());
            StringBuilder state = getState();
            int currentTurn = connect4Dto.getGame().getCurrentTurn();

            if (QTable.hasNextState(currentTurn, state.toString())) {
                String nextState = QTable.getNextState(connect4Dto.getGame().getCurrentTurn(), state.toString());
                int action = Environment.getLocation(Environment.getCurrentTurn());
                double reward=0.0;
                if(Environment.getActivePlayer()==Connect4.PLAYER1){
                     reward= Environment.getTotalRewardP1();
                }else {
                    reward = Environment.getTotalRewardP2();
                }
                updateQValue(state.toString(), action, reward, nextState);//update to RL Qtable
                
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
