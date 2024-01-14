package Test;

import ReinforceLearning.Connect4Handler.ReinforceLearningAgentConnectFour;
import dto.Connect4Dto;
import target.Connect4;

import java.util.Scanner;

public class TestAgent {

        public static void main(String[] args) {
            //train agent// choose human or self learn or train agent
            //for human interaction episodes=1;
            Connect4 game=new Connect4();
            Connect4Dto connect4Dto=new Connect4Dto(game);

            Scanner keyboard = new Scanner(System.in);
            System.out.print("Enter Number of Test Agent\ninput 1:human interaction\ninput more than 1:self-train\ninput:");
            int trainNum=100;
            for (int i = 0; i < trainNum; i++) {
                double process = (double) i / trainNum * 100;
                ReinforceLearningAgentConnectFour Agent = new ReinforceLearningAgentConnectFour(connect4Dto);
                Agent.testAgent();
                if (process < 100) {
                    System.out.println("Train: In process..." + process + "/100%: "+i+"|"+trainNum);
                }

            }
            System.out.println("Test:Completed");
        }

}
