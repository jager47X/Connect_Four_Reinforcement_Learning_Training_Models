package TrainAgent.prototype;

import Connect4.Connect4;
import ReinforceLearning.ReinforceLearningAgentConnectFour;
import TrainAgent.prototype.LearningAgent;
import dto.Connect4Dto;
import dto.QTableDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Reinforce_Learning implements LearningAgent {
    private final Lock agentLock = new ReentrantLock();
    private final Connect4 game;
    private QTableDto Qtable ;

    public Reinforce_Learning() {
        this.game = new Connect4();
    }

    @Override
    public List<String> train() {
        Connect4Dto connect4Dto = new Connect4Dto(game);
        ReinforceLearningAgentConnectFour agent = new ReinforceLearningAgentConnectFour(connect4Dto, Qtable);

        if (agentLock.tryLock()) {
            try {
                Qtable = agent.ReinforceLearning();

                if (Qtable.getHashedData() == null || Qtable.getHashedData().isEmpty()) {
                    System.out.println("Thread " + Thread.currentThread().getId() + ": Warning - hashedData is null or empty.");
                    return new ArrayList<>();
                } else {
                    System.out.println("Thread " + Thread.currentThread().getId() + ": hashedData is " + Qtable.getHashedData());
                }

                // Convert QTableDto to List<String> or handle appropriately
                return convertQTableDtoToList(Qtable);
            } finally {
                agentLock.unlock();
            }
        } else {
            System.out.println("Thread " + Thread.currentThread().getId() + ": Unable to acquire agentLock within the timeout.");
            return new ArrayList<>();
        }
    }

    private List<String> convertQTableDtoToList(QTableDto qTableDto) {
        // Your conversion logic from QTableDto to List<String>
        // Replace this with the actual conversion logic
        return new ArrayList<>();
    }

    @Override
    public List<String> call() throws Exception {
        return train();
    }
}
