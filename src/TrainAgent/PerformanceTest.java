package TrainAgent;

import ReinforceLearning.ReinforceLearningAgentConnectFour;
import dao.QTableDao;
import dto.Connect4Dto;
import target.Connect4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PerformanceTest {

        public static void main(String[] args) {
            final int session=10;
            int input = 0, turn=0;
            GameEnviroment.Connect4 game = new GameEnviroment.Connect4();
            Scanner keyboard = new Scanner(System.in);
            QTableDao qTableDao = QTableDao.getInstance();
            List<String> exportingData = new ArrayList<>();
            Connect4Dto connect4Dto = new Connect4Dto(game);
            ReinforceLearningAgentConnectFour Agent = new ReinforceLearningAgentConnectFour(connect4Dto);

            for (int i = 0; i < session; i++) {
                 game.resetBoard();
                game.setActivePlayer(Connect4.PLAYER2);

                do {
                    game.setActivePlayer((game.getActivePlayer() == Connect4.PLAYER2) ? Connect4.PLAYER1 : Connect4.PLAYER2);
                    turn++;
                    game.setTurn(turn);
                    if (game.isEmpty()) {//check if there empty cell
                        do {
                            //displayboard
                            game.displayBoard();

                            if(game.getActivePlayer() == Connect4.PLAYER1) {
                                System.out.println( "player1: select between 1-7>> " );
                                input= keyboard.nextInt();
                            }else{
                                System.out.println( "AI : select between 1-7>> " );
                                input= Agent.selectAction(0);
                            }

                            if (game.playerDrop(input)) {
                                System.out.println((game.getActivePlayer() == Connect4.PLAYER1) ? "player1: selected "+ input : "player2: selected" + input);
                                break;
                            }
                        } while (true);
                    }//chip is set

                    if (game.winCheck()) {//check winner 1 or 2
                        System.out.println((game.getActivePlayer() == Connect4.PLAYER1) ? "player1 Won!" : "player2 Won!");
                        game.displayBoard();
                        System.out.println("total reward player1:"+game.getTotalRewardP1());
                        System.out.println("total reward player2:"+game.getTotalRewardP2());
                        break;
                    }else if(game.getWinner()==0){//withdraw
                        System.out.println("Withdraw!");
                        game.displayBoard();
                        System.out.println("total reward player1:"+game.getTotalRewardP1());
                        System.out.println("total reward player2:"+game.getTotalRewardP2());
                        break;
                    }
                    System.out.println("total reward player1:"+game.getTotalRewardP1());
                    System.out.println("total reward player2:"+game.getTotalRewardP2());
                } while (game.isEmpty());

                keyboard.close();



            }
            System.out.print("exporting....");
            qTableDao.exportCSV(exportingData,"trained_model_vs_Human.csv");
            }






}
