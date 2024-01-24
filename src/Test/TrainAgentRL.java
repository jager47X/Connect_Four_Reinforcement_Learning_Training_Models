package Test;

import ReinforceLearning.ReinforceLearningAgentConnectFour;
import com.sun.management.OperatingSystemMXBean;
import dao.BaseDao;
import dao.QTableDao;
import dto.Connect4Dto;
import dto.QTableDto;
import target.Connect4;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class TrainAgentRL implements Callable<List<String>> {
    int trainNum;
    private final Lock agentLock = new ReentrantLock();
    private static final long startTime = System.currentTimeMillis();
    public TrainAgentRL(int trainNum) {
        this.trainNum = trainNum;
    }

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
                QTableDto Qtable = agent.ReinforceLearning();

                if (Qtable.getHashedData() == null || Qtable.getHashedData().isEmpty()) {
                    //           System.out.println("Thread " + Thread.currentThread().getId() + ": Warning - hashedData is null or empty.");
                    return new ArrayList<>();
                }

                return Qtable.getHashedData();
            } finally {
                agentLock.unlock();
            }
        } else {
            // Handle inability to acquire the lock within the timeout
            System.out.println("Thread " + Thread.currentThread().getId() + ": Unable to acquire agentLock within the timeout.");
            return new ArrayList<>();
        }
    }



    public static void main(String[] args) throws  InterruptedException {
        int episodes =1000; // Adjust
        final int nThread=2000;
        final int nTrain=2000;
        final int nLoop=nTrain/episodes;
        System.out.print("Importing the Data....");
        QTableDao qTableDao = QTableDao.getInstance();

        List<String> exportingData = new ArrayList<>();
        if(!BaseDao.getImportedGame().isEmpty()){
            for (int i = 0; i < BaseDao.getImportedGame().size(); i++) {
                exportingData.addAll(BaseDao.getImportedGame().get(i));
            }

        }
        int trainIndex=0;
        for (int i = 0; i < nLoop; i++) {
            Thread.sleep(2000);
            // Import game once

            ExecutorService executor = Executors.newFixedThreadPool(nThread);
            List<Future<List<String>>> futures = new ArrayList<>();


            int totalTrain=episodes * nLoop;
            try {
                for (int episode = 0; episode < episodes; episode++) {
                    TrainAgentSL trainAgent = new TrainAgentSL(episode);
                    Future<List<String>> future = executor.submit(trainAgent);
                    futures.add(future);
                    System.out.print("Strating Thread: #"+episode+", ");
                    Thread.sleep(monitorCPUUsage());
                }
                long speed=0,time=0;
                // Wait for all threads to finish
                for (Future<List<String>> future : futures) {

                    trainIndex++;


                    try {

                        System.out.println("Processing thread: " + Thread.currentThread().getId() + ", Index: " + trainIndex);

                        System.out.print("Saving the game... ");
                        long startTimeThread = System.currentTimeMillis();
                        List<String> threadResult = future.get(5, TimeUnit.SECONDS); // Set your timeout value
                        long endTimeThread = System.currentTimeMillis();
                        System.out.println("Thread execution time: " + (endTimeThread - startTimeThread) + "ms");

                        if (threadResult != null) {
                            Thread.sleep(monitorCPUUsage());
                            System.out.print("Thread adding data... ");
                            exportingData.addAll(threadResult);
                            System.out.println(future.state());
                        } else {
                            System.out.println("Thread result is null. Check the TrainAgent.train() method.");
                                        }

                        Thread.sleep(monitorCPUUsage());
                        if (trainIndex < 2) {
                            speed = System.currentTimeMillis() - startTime;
                            time = speed * episodes;
                        }
                        time -= speed;
                        Duration totalTime = Duration.ofMillis(time);
                        System.out.println("Estimate training time: " + formatDuration(totalTime) + " Completed: " + trainIndex + "/" + totalTrain);

                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();

                        //handle failed trainIndex
                        break;
                        // Handle the TimeoutException as needed
                    }

                }
            } finally {
                executor.shutdownNow(); // Shutdown the executor immediately in case of exceptions
            }


            System.out.print("exporting....");
            qTableDao.exportCSV(exportingData,"Reinforce_Learning_policyNetwork.csv");

        }

    }

    private static long monitorCPUUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        // Print overall CPU usage
        System.out.println("CPU Usage: " +osBean.getCpuLoad() * 100 + "%");
        double wait=10000;

        wait*= osBean.getCpuLoad();
        if(wait<0){
            wait=1000;
        }
        //    System.out.println("wait: " +wait /1000 + "second");

        return (long)wait;
    }
    private static String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return String.format("%d min, %d sec", minutes, seconds);
    }
}
