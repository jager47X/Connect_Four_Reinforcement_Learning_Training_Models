package TrainAgent;


import ReinforceLearning.ReinforceLearningAgentConnectFour;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.management.OperatingSystemMXBean;
import dto.Connect4Dto;
import dto.QEntry;
import dto.QTableDto;
import GameEnviroment.Connect4;
import dto.QTableExportDto;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class Reinforcement_Learning implements Callable<QTableDto> {

    static QTableDto Qtable=new QTableDto();
    private final Lock agentLock = new ReentrantLock();
    private static final long startTime = System.currentTimeMillis();

    @Override
    public QTableDto call( )  {
        return train();
    }

    private QTableDto train(){

        if (agentLock.tryLock()) { try {
            Connect4 game = new Connect4();
            Connect4Dto connect4Dto = new Connect4Dto(game);
            ReinforceLearningAgentConnectFour agent = new ReinforceLearningAgentConnectFour(connect4Dto, Qtable);

            Qtable = agent.ReinforceLearning();

            if (Qtable.getHashedData() == null || Qtable.getHashedData().isEmpty()) {
                System.out.println("Warning - hashedData is null or empty.");
                return new QTableDto();
            } else {
                System.out.println("hashedData is " + Qtable.getHashedData());
                return Qtable;
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



    public static void main(String[] args)  {
        final int THREAD_POOL_SIZE =200; // Adjust
        final int TOTAL_THREADS=200;
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

                // Wait for all threads to finish
                for (Future<QTableDto> future : futures) {

                    trainIndex++;


                    try {

                        System.out.println("Processing TOTAL_THREADS: " + Thread.currentThread().getName() + ", Index: " + trainIndex);
                        long startTimeThread = System.currentTimeMillis();
                        System.out.print("Saving the game... ");

                        QTableDto threadResult = future.get(5, TimeUnit.SECONDS); // Set your timeout value


                        if (threadResult != null) {

                            monitorCPUUsage();
                            System.out.print("Thread adding data... ");

                            processQTable(threadResult);

                            System.out.println(future.state());
                            long endTimeThread = System.currentTimeMillis();
                            System.out.println("Thread execution time: " + (endTimeThread - startTimeThread) + "ms");

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
            // Save QTable to JSON file after processing all threads
            saveQTableToGson(Qtable);

            Duration totalTime = Duration.ofMillis(System.currentTimeMillis() - startTime);
            System.out.println("Total execution time: " + formatDuration(totalTime));

        }

    }

    private static void processQTable(QTableDto qTable) {// merge QTables

        // perform statistical analysis
        int totalEntries = 0;
        int totalUniqueStates = qTable.getExportingPolicyNetWork().size();
        int maxActionsForState = 0;

        for (Set<QEntry> entries : qTable.getExportingPolicyNetWork().values()) {
            totalEntries += entries.size();
            maxActionsForState = Math.max(maxActionsForState, entries.size());
        }

        double averageActionsPerState = (double) totalEntries / totalUniqueStates;

        System.out.println("Statistical Analysis:");
        System.out.println("Total Unique States: " + totalUniqueStates);
        System.out.println("Total Entries: " + totalEntries);
        System.out.println("Max Actions for a State: " + maxActionsForState);
        System.out.println("Average Actions per State: " + averageActionsPerState);
        System.out.println("-------------------------");
        monitorCPUUsage();
    }

    private static void saveQTableToGson(QTableDto qTable) {

        QTableExportDto exportDto = new QTableExportDto();
        System.out.println("TrainedModel.json is "+qTable.getExportingPolicyNetWork());
        exportDto.setExportingPolicyNetWork(qTable.getExportingPolicyNetWork());
        // Set other fields if needed

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(exportDto);

        try (Writer writer = new FileWriter("TrainedModel.json")) {
            writer.write(jsonString);
        } catch (IOException e) {
            System.err.println("Error saving QTable to JSON: " + e.getMessage());
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

