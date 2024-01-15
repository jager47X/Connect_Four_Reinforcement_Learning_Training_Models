package Test;

import ReinforceLearning.Connect4Handler.ReinforceLearningAgentConnectFour;
import dto.Connect4Dto;
import target.Connect4;

import java.util.Scanner;

public class TestAccuracy {
    public static void main(String[] args) {
        //train agent// choose human or self learn or train agent
        //for human interaction episodes=1;
        Connect4 game=new Connect4();
        Connect4Dto connect4Dto=new Connect4Dto(game);

        Scanner keyboard = new Scanner(System.in);
        ReinforceLearningAgentConnectFour Agent = new ReinforceLearningAgentConnectFour(connect4Dto);
        Agent.testAgent(1);

        System.out.println("Test:Completed");
    }
}
