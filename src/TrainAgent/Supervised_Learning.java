package TrainAgent;

import ReinforceLearning.ReinforceLearningAgentConnectFour;
import com.sun.management.OperatingSystemMXBean;
import dao.BaseDao;
import dao.QTableDao;
import dto.Connect4Dto;
import dto.QTableDto;
import GameEnviroment.Connect4;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Supervised_Learning implements Callable<List<String>> {


    private final Lock agentLock = new ReentrantLock();
    private static final long startTime = System.currentTimeMillis();


    @Override
    public List<String> call() {
        //        System.out.println("Thread " + Thread.currentThread().getId() + " is running.");
        return train();
    }

    private List<String> train() {
        Connect4 game = new Connect4();
        Connect4Dto connect4Dto = new Connect4Dto(game);
        ReinforceLearningAgentConnectFour agent = new ReinforceLearningAgentConnectFour(connect4Dto);


        if (agentLock.tryLock()) {
            try {
                QTableDto Qtable = agent.SupervisedLearning();

                if (Qtable.getHashedData() == null || Qtable.getHashedData().isEmpty()) {
                          return new ArrayList<>();
                }

                return Qtable.getHashedData();
            } finally {
                agentLock.unlock();
            }
        } else {
            // Handle inability to acquire the lock within the timeout
            System.out.println("Thread " + Thread.currentThread().getName()+ ": Unable to acquire agentLock within the timeout.");
            return new ArrayList<>();
        }
    }



    public static void main(String[] args)  {
        final int THREAD_POOL_SIZE =2000; // Adjust
        final int TOTAL_THREADS=2000;
        final int TOTAL_TRAINING_EPISODES=100000;
        final int LOOP=TOTAL_TRAINING_EPISODES/TOTAL_THREADS;


        QTableDao qTableDao = QTableDao.getInstance();

        List<String> exportingData = new ArrayList<>();
        if(!BaseDao.getImportedGames().isEmpty()){
            for (int i = 0; i < BaseDao.getImportedGames().size(); i++) {
                exportingData.addAll(BaseDao.getImportedGames().get(i));
            }

        }
        int trainIndex=0;
        for (int i = 0; i < LOOP; i++) {

            // Import game once

            ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            List<Future<List<String>>> futures = new ArrayList<>();

            try {
                for (int episode = 0; episode < TOTAL_THREADS; episode++) {

                    Supervised_Learning trainAgent = new Supervised_Learning();

                    Future<List<String>> future = executor.submit(trainAgent);
                    futures.add(future);
                    System.out.print("Starting Thread: #"+episode+", ");

                }

                // Wait for all threads to finish
                for (Future<List<String>> future : futures) {


                    try {

                        System.out.println("Processing TOTAL_THREADS: " + Thread.currentThread().getName() + ", Index: " + trainIndex);
                        double  startTimeThread = System.currentTimeMillis();
                        System.out.print("Saving the game... ");
                        while(!future.isDone()) {
                            System.out.println("Calculating...");
                            Thread.sleep(3000);
                        }

                        List<String> threadResult = future.get(5, TimeUnit.SECONDS); // Set your timeout value


                        if (threadResult != null) {
                            exportingData.addAll(threadResult);
                            trainIndex++;
                            System.out.print("Future state: ");
                            System.out.println(future.state());

                            double endTimeThread = System.currentTimeMillis();
                            double executionTime= endTimeThread - startTimeThread;
                            System.out.println("Thread execution time: " + executionTime + "ms");
                            monitorCPUUsage();
                        } else {
                            System.out.println("Thread result is null. Check the TrainAgent.train() method.");
                        }


                        System.out.println(" Completed: " + trainIndex + "/" + TOTAL_TRAINING_EPISODES);

                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        System.out.println(  e.getMessage());

                        //handle failed trainIndex
                        break;
                        // Handle the TimeoutException as needed
                    }

                }
            } finally {
                executor.shutdownNow(); // Shutdown the executor immediately in case of exceptions
            }

            System.out.print("exporting....");
            qTableDao.exportCSV(exportingData,"Supervised_Learning_policyNetwork.csv");

            Duration totalTime = Duration.ofMillis( System.currentTimeMillis()-startTime);
            System.out.println("Total execution time: " + formatDuration(totalTime));

        }

    }

    private static void monitorCPUUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        // Print overall CPU usage
        System.out.println("CPU Usage: " +osBean.getCpuLoad() * 100 + "%");

    }
    private static String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return String.format("%d min, %d sec", minutes, seconds);
    }
}