package ReinforceLearning.Connect4Handler;

import ReinforceLearning. AbstractReinforceLearningAgent2D;
import dto.Connect4Dto;
import dto.QTableDto;
import target.Connect4;
import target.RuleBasedAI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class  ReinforceLearningAgentConnectFour  extends AbstractReinforceLearningAgent2D {
    int TotalReward;

    public ReinforceLearningAgentConnectFour (Connect4Dto connect4dto) {
        super(connect4dto);
        TotalReward=0;
    }
    @Override
    public QTableDto SupervisedLeanrning() {//use multi-thread //if epsodes=1 then vs human if not ai vs ai


        QTableDto qTableDto=new QTableDto(Environment);
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
                qTableDto.addLine();
                break;
            }else{
                qTableDto.addLine();
            }
        }while(!isGameOver(Environment));


return qTableDto;
    }

    @Override
    public QTableDto ReinforceLearning() {//use multi-thread //if epsodes=1 then vs human if not ai vs ai

        QTableDto qTableDto=new QTableDto(Environment);
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
            System.out.print("Player");
            if (Environment.getActivePlayer() == Connect4.PLAYER1) {//switch player
                Environment.setActivePlayer(Connect4.PLAYER2);
                Environment.setNonActivePlayer(Connect4.PLAYER1);
                System.out.println("1's turn");
            } else {
                Environment.setActivePlayer(Connect4.PLAYER1);
                Environment.setNonActivePlayer(Connect4.PLAYER2);
                System.out.println("2's turn");
            }


            Environment.playerDrop(selectAction());
            Environment.displayBoard();
            if (Environment.winCheck()||Environment.getWinner()==0) {//check winner 1 or 2
                qTableDto.addLine();
                break;
           }else{
                qTableDto.addLine();
            }
        }while(!isGameOver(Environment));

return qTableDto;
    }


    public void resetBoard(Connect4 game) {
        game.resetBoard();
    }


    private boolean isGameOver(Connect4 game) {
        return game.getWinner() != -1;
    }


    @Override
    public int[] getLegalActions() {//not =-6
        List<Integer> legalActionsList = new ArrayList<>();
// Iterate over each column to check if it's a legal action
            for (int col = 0; col < Environment.getCOLS_SIZE(); col++) {
                if (Environment.isValidColumn(col)) {
                    legalActionsList.add(col+1);
                }
            }

        // Convert the list of legal actions to an array
        int[] legalActions = new int[legalActionsList.size()];
        for (int i = 0; i < legalActions.length; i++) {
            legalActions[i] = legalActionsList.get(i);
        }
        System.out.println("legal action:"+ Arrays.toString(legalActions));
        return legalActions;
    }

    @Override
    public String stateToIndex(Connect4Dto dto) {
        Connect4 currentBoard=dto.getGame();
       int turn= currentBoard.getCurrentTurn();
        StringBuilder state = new StringBuilder( );
          for (int i = 0; i < turn; i++) {//add up all action
              if (i % 2==0) {
                  state.append(1);
              }else{
                  state.append(2);
              }
              state.append(currentBoard.getLocation(i));
        }
        System.out.println("Current state:"+state);
          return state.toString();
    }



    @Override
    public int selectAction() {
        return super.selectAction();
    }


}
