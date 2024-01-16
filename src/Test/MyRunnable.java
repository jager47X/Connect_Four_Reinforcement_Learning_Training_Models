package Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRunnable implements Runnable {
    private int episode;

    public MyRunnable(int episode) {
        this.episode = episode;
    }

    @Override
    public void run() {
        // Your task logic goes here
        System.out.println("Executing episode: " + episode);
    }

    public static void main(String[] args) {
        int episodes = 10; // Adjust based on your needs
        ExecutorService executor = Executors.newFixedThreadPool(episodes);

        for (int episode = 0; episode < episodes; episode++) {
            Runnable myRunnable = new MyRunnable(episode);
            executor.execute(myRunnable);
        }

        // Shutdown the executor to release resources
        executor.shutdown();
    }
}
