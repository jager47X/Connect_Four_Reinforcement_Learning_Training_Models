package TrainAgent.ignore;

import ReinforceLearning.ReinforceLearningAgentConnectFour;
import com.sun.management.OperatingSystemMXBean;
import dao.BaseDao;
import dao.QTableDao;
import dto.Connect4Dto;
import dto.QTableDto;
import Connect4.Connect4;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class Reinforce_Learning implements Callable<List<String>> {

    QTableDto Qtable=new QTableDto();
    private final Lock agentLock = new ReentrantLock();
    private static long startTime = System.currentTimeMillis();


    @Override
    public List<String> call() {
        //        System.out.println("Thread " + Thread.currentThread().getId() + " is running.");
        return train();
    }

    private List<String> train() {
        Connect4 game = new Connect4();
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



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final int thread =20; // Adjust
        final int nTrain=100;
        final int nLoop=nTrain/thread;


        QTableDao qTableDao = QTableDao.getInstance();

        List<String> exportingData = new ArrayList<>();
        if(!BaseDao.getImportedGames().isEmpty()){
            for (int i = 0; i < BaseDao.getImportedGames().size(); i++) {
                exportingData.addAll(BaseDao.getImportedGames().get(i));
            }

        }
        int trainIndex=0;
        for (int i = 0; i < nLoop; i++) {

            // Import game once

            ExecutorService executor = Executors.newFixedThreadPool(thread);
            List<Future<List<String>>> futures = new ArrayList<>();

            try {
                for (int episode = 0; episode < thread; episode++) {

                    Reinforce_Learning trainAgent = new Reinforce_Learning();
                    Future<List<String>> future = executor.submit(trainAgent);
                    futures.add(future);
                    System.out.print("Starting Thread: #"+episode+", ");

                }

                // Wait for all threads to finish
                for (Future<List<String>> future : futures) {
                    trainIndex++;


                    try {

                        System.out.println("Processing thread: " + Thread.currentThread().getId() + ", Index: " + trainIndex);
                        long startTimeThread = System.currentTimeMillis();
                        System.out.print("Saving the game... ");

                        List<String> threadResult = future.get(5, TimeUnit.SECONDS); // Set your timeout value


                        if (threadResult != null) {

                            System.out.print("Thread adding data... ");
                            exportingData.addAll(threadResult);
                            System.out.println(future.state());
                            long endTimeThread = System.currentTimeMillis();
                            System.out.println("Thread execution time: " + (endTimeThread - startTimeThread) + "ms");

                        } else {
                            System.out.println("Thread result is null. Check the TrainAgent.train() method.");
                        }


                        monitorCPUUsage();
                        System.out.println(" Completed: " + trainIndex + "/" + nTrain);

                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();

                        //handle failed trainIndex
                        break;
                        // Handle the TimeoutException as needed
                    }

                }
            } finally {
                executor.shutdownNow(); // Shutdown the executor immediately in case of exceptions
                try {
                    if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                        executor.shutdownNow(); // Forceful shutdown if necessary
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow(); // Handle interruption
                    Thread.currentThread().interrupt(); // Preserve the interrupted status
                }
            }


        }
        System.out.print("exporting....");
        qTableDao.exportCSV(exportingData,"Supervised_Learning_policyNetwork.csv");

        Duration totalTime = Duration.ofMillis( System.currentTimeMillis()-startTime);
        System.out.println("Total execution time: " + formatDuration(totalTime));


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
