import dao.Connect4Dao;
import dto.Connect4Dto;
import target.Connect4;
import dto.Connect4Dto.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int input = 0, turn=0;
        //AI bot;
        Connect4 game = new Connect4();
        Scanner keyboard = new Scanner(System.in);
        game.setActivePlayer(game.PLAYER2);
        Connect4Dto dto=new Connect4Dto(game);
        do {
            game.setActivePlayer((game.getActivePlayer() == game.PLAYER2) ? game.PLAYER1 : game.PLAYER2);
            turn++;
            game.setTurn(turn);
            if (game.CheckCells()) {//check if there empty cell
                do {
                    game.DisplayBoard();
                    System.out.println((game.getActivePlayer() == game.PLAYER1) ? "player1: select between 1-7>>" : "player2: select between 1-7>>");
                    input = keyboard.nextInt();//port

                    if (game.PlayerDrop(input)) {
                        System.out.println((game.getActivePlayer() == game.PLAYER1) ? "player1: selected "+ input : "player2: selected" + input);
                        break;
                    }
                } while (true);

            }//chip is set

            if (game.WinCheck()) {//check win
                System.out.println((game.getActivePlayer() == game.PLAYER1) ? "player1 Won" : "player2 Won");
                game.DisplayBoard();
                System.out.println(dto.hashing());// to board
                break;
            }else{
                System.out.println(dto.hashing());
            }


        } while (game.CheckCells());


    }


}

