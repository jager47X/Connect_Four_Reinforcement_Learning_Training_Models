package Test;

import ReinforceLearning.Connect4Handler.ReinforceLearningAgentConnectFour;
import dao.BaseDao;
import dao.QTableDao;
import dto.Connect4Dto;
import dto.QTableDto;
import target.Connect4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.Callable;


    public class TrainAgent implements Callable<List<String>> {
        int trainNum;

        public TrainAgent(int trainNum) {
            this.trainNum = trainNum;
        }

        @Override
        public List<String> call() {
            System.out.println("Thread " + Thread.currentThread().getId() + " is running.");
            return train();
        }

        public List<String> train() {
            Connect4 game = new Connect4();
            Connect4Dto connect4Dto = new Connect4Dto(game);
            ReinforceLearningAgentConnectFour agent = new ReinforceLearningAgentConnectFour(connect4Dto);
            QTableDto Qtable = agent.trainAgent();

            if (Qtable.getHashedData() == null || Qtable.getHashedData().isEmpty()) {
                System.out.println("Thread " + Thread.currentThread().getId() + ": Warning - hashedData is null or empty.");
                return new ArrayList<>();
            } else {
                System.out.println("Thread " + Thread.currentThread().getId() + ": hashedData is " + Qtable.getHashedData());
            }

            return Qtable.getHashedData();
        }
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Import game once
        QTableDao qTableDao = QTableDao.getInstance();
        qTableDao.import_CSV();
        List<String> exportingData = new ArrayList<>();
        if(!BaseDao.getImportedData().isEmpty()){
            exportingData.addAll(BaseDao.getImportedData());
        }
        int episodes = 20; // Adjust based on your needs

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<List<String>>> futures = new ArrayList<>();

        try {
            for (int episode = 0; episode < episodes; episode++) {
                TrainAgent trainAgent = new TrainAgent(episode);
                Future<List<String>> future = executor.submit(trainAgent);
                futures.add(future);
            }

            // Wait for all threads to finish
            for (Future<List<String>> future : futures) {
                try {
                    List<String> threadResult = future.get();
                    if (threadResult != null) {
                        System.out.println("Adding: " + threadResult);
                        exportingData.addAll(threadResult);
                    } else {
                        System.out.println("Thread result is null. Check the TrainAgent.train() method.");
                        // You may want to handle this case based on your application's requirements
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdownNow(); // Shutdown the executor immediately in case of exceptions
        }
        qTableDao.exportCSV(exportingData);
    }
}
