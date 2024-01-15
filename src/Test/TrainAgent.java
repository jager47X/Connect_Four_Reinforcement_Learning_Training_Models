package Test;

import ReinforceLearning.Connect4Handler.ReinforceLearningAgentConnectFour;
import dto.Connect4Dto;
import target.Connect4;

import java.util.Scanner;

public class TrainAgent {//cannot train 5++ at a time due to thread error

        public static void main(String[] args) {
            int trainNum=5;
            for (int i = 0; i < trainNum; i++) {
                double process = (double) i / trainNum * 100;
                Connect4 game = new Connect4();
                Connect4Dto connect4Dto = new Connect4Dto(game);
                ReinforceLearningAgentConnectFour Agent = new ReinforceLearningAgentConnectFour(connect4Dto);
                Agent.trainAgent();
                if (process < 100) {
                    System.out.println("train: In process..." + process + "/100%: "+i+"|"+trainNum);
                }
            }
            System.out.println("train: 100/100%: "+trainNum+"|"+trainNum);

        }
}
