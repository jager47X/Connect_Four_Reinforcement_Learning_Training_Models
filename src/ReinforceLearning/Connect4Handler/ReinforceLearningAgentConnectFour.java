package ReinforceLearning.Connect4Handler;

import ReinforceLearning. AbstractReinforceLearningAgent2D;
import dto.Connect4Dto;
import dto.QTableDto;
import target.Connect4;
import target.RuleBasedAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class  ReinforceLearningAgentConnectFour  extends AbstractReinforceLearningAgent2D {
    int TotalReward;

    public ReinforceLearningAgentConnectFour (Connect4Dto connect4dto) {
        super(connect4dto);
        TotalReward=0;
    }
    @Override
    public QTableDto SupervisedLeanrning() {//use multi-thread //if epsodes=1 then vs human if not ai vs ai


        QTableDto qTableDto=new QTableDto(Environment);
        Scanner keyboard = new Scanner(System.in);
        //  for (int i = 0; i <episodes ; i++) {//set how many times to play
        Environment.setActivePlayer(Connect4.PLAYER2);
        Environment.setTurn(0);
        setQTable(QTable);

        //USE AI
        resetBoard(Environment);
        do {
            if(isGameOver(Environment)){
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
                qTableDto.addLine(Environment.getTurn());
                break;
            }else{
                qTableDto.addLine(Environment.getTurn());
            }
        }while(!isGameOver(Environment));


return qTableDto;
    }

    @Override
    public QTableDto ReinforceLearning() {//use multi-thread //if epsodes=1 then vs human if not ai vs ai

        QTableDto qTableDto=new QTableDto(Environment);
        Scanner keyboard = new Scanner(System.in);
        //  for (int i = 0; i <episodes ; i++) {//set how many times to play
        Environment.setActivePlayer(Connect4.PLAYER2);
        Environment.setTurn(0);
        setQTable(QTable);

        //USE AI
        resetBoard(Environment);
        do {
            if(isGameOver(Environment)){
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
            Environment.playerDrop(selectAction());
            if (Environment.winCheck()||Environment.getWinner()==0) {//check winner 1 or 2
                qTableDto.addLine(Environment.getTurn());
                break;
           }else{
                qTableDto.addLine(Environment.getTurn());
            }
        }while(!isGameOver(Environment));

return qTableDto;
    }




    public void resetBoard(Connect4 game) {
        game.resetBoard();
    }


    @Override
    public boolean makeMove(int action) {
        return  Environment.playerDrop(action);
    }



    private boolean isGameOver(Connect4 game) {
        return game.getWinner() != -1;
    }


    private void printBoard(Connect4 game) {
        game.displayBoard();
    }

    @Override
    public int[] getLegalActions() {
        List<Integer> legalActionsList = new ArrayList<>();
// Iterate over each column to check if it's a legal action
            for (int col = 0; col < Environment.getCOLS_SIZE(); col++) {
                if (Environment.isValidColumn(col)) {
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
    public String stateToIndex(Connect4Dto state) {
         String gameStatus=state.toIndex();
         //split reward
         String[] parsed=gameStatus.split("R");
        System.out.println("stateIndex:"+parsed);
         TotalReward= Integer.parseInt(parsed[1]);
        return parsed[0];
    }



    @Override
    public int selectAction() {
        return super.selectAction();
    }


}
