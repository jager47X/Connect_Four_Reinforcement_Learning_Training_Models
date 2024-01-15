package Test;

import dao.QTableDao;
import dto.QTableDto;
import target.Connect4;
import target.RuleBasedAI;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TestRuleBasedAI {

      public static void main(String[] args) {
          long start = System.currentTimeMillis();
        int input = 0, turn=0;
        Connect4 game = new Connect4();
        Scanner keyboard = new Scanner(System.in);
        game.setActivePlayer(Connect4.PLAYER2);
          QTableDao dao=QTableDao.getInstance();
          QTableDto dto= new QTableDto(game);
        do {
            game.setActivePlayer((game.getActivePlayer() == Connect4.PLAYER2) ? Connect4.PLAYER1 : Connect4.PLAYER2);
            turn++;
            game.setTurn(turn);
            if (game.isEmpty()) {//check if there empty cell
                do {
                    //display-board
                    game.displayBoard();
                    //Input
                    if(game.getActivePlayer() == Connect4.PLAYER1){
                        System.out.println("player1: select between 1-7>>" );
                        input= RuleBasedAI.makeMove(game.getBoard(),Connect4.PLAYER1,Connect4.PLAYER2);
                    }else{
                        System.out.println("Player 2: select between 1-7>>" );
                        input=RuleBasedAI.makeMove(game.getBoard(),Connect4.PLAYER2,Connect4.PLAYER1);
                    }

                    if (game.playerDrop(input)) {
                        System.out.println((game.getActivePlayer() == Connect4.PLAYER1) ? "player1: selected "+ input : "player2: selected" + input);
                        break;
                    }
                    //check AI drop if it is valid
                } while (true);

            }//chip is set

            if (game.winCheck()) {//check winner 1 or 2
                System.out.println((game.getActivePlayer() == Connect4.PLAYER1) ? "player1 Won!" : "player2 Won!");
                game.displayBoard();
                dao.addLine(turn,dto);
                dto.exportCSV();
                break;
            }else if(game.getWinner()==0){//withdraw
                System.out.println("Withdraw!");
                game.displayBoard();
                dao.addLine(turn,dto);
                dto.exportCSV();
                break;
            }else{//game keep goinh
                dao.addLine(turn,dto);
            }


        } while (game.isEmpty());//game is over

        keyboard.close();
          long end = System.currentTimeMillis();
          System.out.println("Elapsed Time in milli seconds: "+ (end-start)); }


}

