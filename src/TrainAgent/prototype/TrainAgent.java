package TrainAgent.prototype;

import java.util.concurrent.ExecutionException;

import TrainAgent.prototype.Supervised_Learning;
import com.sun.management.OperatingSystemMXBean;
import dao.BaseDao;
import dao.QTableDao;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import dto.QTableDto;



public class TrainAgent {
    private static final long startTime = System.currentTimeMillis();

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final int thread = 20;
        final int nTrain = 100;
        final int nLoop = nTrain / thread;

        QTableDao qTableDao = QTableDao.getInstance();
        qTableDao.setImportingModel("TEST_Supervised_Learning_policyNetwork.csv");

        // Initialize Qtable in TrainAgent
        QTableDto qTableDto = new QTableDto();

        List<String> exportingData = new ArrayList<>();
        if (!BaseDao.getImportedGames().isEmpty()) {
            for (int i = 0; i < BaseDao.getImportedGames().size(); i++) {
                exportingData.addAll(BaseDao.getImportedGames().get(i));
            }
        }

        int trainIndex = 0;
        for (int i = 0; i < nLoop; i++) {
            ExecutorService executor = Executors.newFixedThreadPool(thread);
            List<Future<List<String>>> futures = new ArrayList<>();
            try {
                for (int episode = 0; episode < thread; episode++) {
                    // Pass QTableDto to Supervised_Learning constructor
                    Supervised_Learning trainAgent = new Supervised_Learning(qTableDto);
                    Future<List<String>> future = executor.submit(trainAgent);
                    futures.add(future);
                    System.out.print("Starting Thread: #" + episode + ", ");
                }

                for (Future<List<String>> future : futures) {
                    trainIndex++;
                    try {
                        System.out.println("Processing thread: " + Thread.currentThread().getId() + ", Index: " + trainIndex);
                        long startTimeThread = System.currentTimeMillis();
                        System.out.print("Saving the game... ");
                        List<String> threadResult = future.get(5, TimeUnit.SECONDS);
                        if (!threadResult.isEmpty()) {
                            System.out.print("Thread adding data... ");
                            exportingData.addAll(threadResult);
                            System.out.println(future.state());
                            long endTimeThread = System.currentTimeMillis();
                            System.out.println("Thread execution time: " + (endTimeThread - startTimeThread) + "ms");
                        } else {
                            System.out.println("Thread result is null. Check the LearningAgent.train() method.");
                        }
                        monitorCPUUsage();
                        System.out.println(" Completed: " + trainIndex + "/" + nTrain);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            } finally {
                executor.shutdownNow();
                try {
                    if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }

            System.out.print("Exporting....");
            qTableDao.exportCSV(exportingData, "TEST_Supervised_Learning_policyNetwork.csv");
        }


        Duration totalTime = Duration.ofMillis(System.currentTimeMillis() - startTime);
        System.out.println("Total execution time: " + formatDuration(totalTime));
    }

    private static long monitorCPUUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        // Print overall CPU usage
        System.out.println("CPU Usage: " + osBean.getCpuLoad() * 100 + "%");
        double wait = 10000;

        wait *= osBean.getCpuLoad();
        if (wait < 0) {
            wait = 1000;
        }
        //    System.out.println("wait: " +wait /1000 + "second");

        return (long) wait;
    }

    private static String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return String.format("%d min, %d sec", minutes, seconds);
    }
}
