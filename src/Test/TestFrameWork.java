import target.Connect4;
import target.RuleBasedAI;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        //train agent// choose human or self learn or train agent
        //for human interaction episodes=1;
        int input = 0, turn=0;
        //AI bot;
        Connect4 game = new Connect4();
        Scanner keyboard = new Scanner(System.in);
        game.setActivePlayer(game.PLAYER2);
       // Connect4Dto dto=new Connect4Dto(game);

        do {
            game.setActivePlayer((game.getActivePlayer() == game.PLAYER2) ? game.PLAYER1 : game.PLAYER2);
            turn++;
            game.setTurn(turn);
            if (game.isEmpty()) {//check if there empty cell
                do {
                    //displayboard
                    game.displayBoard();
                    //Input
                    if(game.getActivePlayer() == game.PLAYER1){
                        System.out.println("player1: select between 1-7>>" );
                        input = RuleBasedAI.makeMove(game.getBoard(),Connect4.PLAYER1,Connect4.PLAYER2);
                        //System.out.println("player1: select between 1-7>>" ) ? "player1: select between 1-7>>" : "player2: select between 1-7>>");
                        //input = keyboard.nextInt();//human
                    }else{
                        System.out.println("Player 2: select between 1-7>>" );
                        input = RuleBasedAI.makeMove(game.getBoard(),Connect4.PLAYER2,Connect4.PLAYER1);
                    }
                    //check player drop if it is valid
                    if (game.playerDrop(input)) {
                        System.out.println((game.getActivePlayer() == game.PLAYER1) ? "player1: selected "+ input : "player2: selected" + input);
                        break;
                    }
                } while (true);

            }//chip is set

            if (game.winCheck()) {//check winner 1 or 2

                System.out.println((game.getActivePlayer() == game.PLAYER1) ? "player1 Won!" : "player2 Won!");

                game.displayBoard();
            //    dto.addLine(turn);
             //   dto.exportCSV();
                break;
            }else if(game.getWinner()==0){//withdraw
                System.out.println("Withdraw!");
                game.displayBoard();
              //  dto.addLine(turn);
             //   dto.exportCSV();
                break;
            }else{
              //  dto.addLine(turn);//why 35?
            }


        } while (game.isEmpty());

        keyboard.close();
    }


}

