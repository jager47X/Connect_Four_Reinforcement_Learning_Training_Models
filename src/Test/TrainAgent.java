package Test;

import ReinforceLearning.Connect4Handler.ReinforceLearningAgentConnectFour;
import dto.Connect4Dto;
import target.Connect4;

import java.util.List;

public class TrainAgent implements Runnable {//cannot train 5++ at a time due to thread error
    int trainNum;
    List<String> threadResult;

    public TrainAgent(int trainNum){
        super();
        this.trainNum=trainNum;

    }

        public static List<String> train(){
        Connect4 game = new Connect4();
        Connect4Dto connect4Dto = new Connect4Dto(game);
        ReinforceLearningAgentConnectFour Agent = new ReinforceLearningAgentConnectFour(connect4Dto);
        Agent.trainAgent();

            return //list of game set
        }
        @Override
        public void run(){
        threadResult=train();
        }
        public static void main(String[] args) throws InterruptedException {
         //   for (int i = 0; i < trainNum; i++) {
            //                double process = (double) i / trainNum * 100;
            //                if (process < 100) {
            //                    System.out.println("train: In process..." + process + "/100%: "+i+"|"+trainNum);
            //                }
            //            }
            //            System.out.println("train: 100/100%: "+trainNum+"|"+trainNum);

            int trainNum=5;
            TrainAgent trainAgent=new TrainAgent(trainNum);
            TrainAgent trainAgent2=new TrainAgent(trainNum);
            Thread thread1=new Thread(trainAgent);
            Thread thread2=new Thread(trainAgent);
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
            trainAgent.export;
            trainAgent.export;
            var threadResult=trainAgent.threadResult;

        }
}
