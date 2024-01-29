package TrainAgent.prototype;

import ReinforceLearning.ReinforceLearningAgentConnectFour;
import dto.Connect4Dto;
import dto.QTableDto;
import Connect4.Connect4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public interface LearningAgent extends Callable<List<String>> {
    List<String> train();

}

