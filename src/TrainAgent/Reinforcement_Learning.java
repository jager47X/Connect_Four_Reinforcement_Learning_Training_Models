package TrainAgent;


import ReinforceLearning.ReinforceLearningAgentConnectFour;

import com.sun.management.OperatingSystemMXBean;
import dto.Connect4Dto;

import dto.QTableDto;
import GameEnviroment.Connect4;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class Reinforcement_Learning implements Callable<QTableDto> {

    static QTableDto QTable =new QTableDto();
    private static AtomicReference<List<QTableDto>> ThreadResultList;
    private final Lock agentLock = new ReentrantLock();
    private static final long startTime = System.currentTimeMillis();

    @Override
    public QTableDto call( )  {
        return train();
    }

    private QTableDto train(){
        System.out.println("training");
        if (agentLock.tryLock()) { try {
            Connect4 game = new Connect4();
            Connect4Dto connect4Dto = new Connect4Dto(game);
            ReinforceLearningAgentConnectFour agent = new ReinforceLearningAgentConnectFour(connect4Dto, QTable);

            QTable = agent.ReinforceLearning();

            if (QTable.getHashedData() == null || QTable.getHashedData().isEmpty()) {
                System.out.println("Warning - hashedData is null or empty.");
                return new QTableDto();
            } else {
                System.out.println("hashedData is " + QTable.getHashedData());
                return QTable;
            }
        } finally {
            agentLock.unlock();
        }
        } else {
            // Handle inability to acquire the lock within the timeout
            System.out.println("Thread " + Thread.currentThread().getName() + ": Unable to acquire agentLock within the timeout.");
            return null;
        }
    }



    public static void main(String[] args) throws InterruptedException {
        final int THREAD_POOL_SIZE =100; // Adjust
        final int TOTAL_THREADS=100;
        final int TOTAL_TRAINING_EPISODES=1000;
        final int LOOP=TOTAL_TRAINING_EPISODES/TOTAL_THREADS;



        int trainIndex=0;
        for (int i = 0; i < LOOP; i++) {

            // Import game once

            ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            List<Future<QTableDto>> futures = new ArrayList<>();

            try {
                for (int episode = 0; episode < TOTAL_THREADS; episode++) {

                    Reinforcement_Learning trainAgent = new Reinforcement_Learning();
                    Future<QTableDto> future = executor.submit(trainAgent);
                    futures.add(future);
                    System.out.print("Starting Thread: #"+episode+", ");

                }
                ThreadResultList= new AtomicReference<>(new ArrayList<>());
                // Wait for all threads to finish
                for (Future<QTableDto> future : futures) {


                    try {

                        System.out.println("Processing TOTAL_THREADS: " + Thread.currentThread().getName() + ", Index: " + trainIndex);
                        double startTimeThread = System.currentTimeMillis();
                        System.out.print("Saving the game... ");

                        while(!future.isDone()) {
                            System.out.println("Calculating...");
                            Thread.sleep(3000);
                        }


                        QTableDto threadResult = future.get(5, TimeUnit.SECONDS); // Set your timeout value


                        if (threadResult != null) {
                            ThreadResultList.get().add(threadResult);
                            trainIndex++;
                            System.out.print("Future state: ");
                            System.out.println(future.state());

                            double endTimeThread = System.currentTimeMillis();
                            double executionTime= endTimeThread - startTimeThread;
                            System.out.println("Thread execution time: " + executionTime + "ms");
                            monitorCPUUsage();
                        } else {
                            System.out.println("Thread result is null.");
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
            System.out.print("exporting Json....");
            QTable.ToGson(ThreadResultList);  // Save QTable to JSON file after processing all threads
            Duration totalTime = Duration.ofMillis(System.currentTimeMillis() - startTime);
            System.out.println("Total execution time: " + formatDuration(totalTime));

        }

    }


    private static void monitorCPUUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        // Print overall CPU usage
        if (osBean.getCpuLoad() >0) {
            System.out.println("\nCPU Usage: " +osBean.getCpuLoad() * 100 + "%");
        }

    }
    private static String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return String.format("%d min, %d sec", minutes, seconds);
    }


}

