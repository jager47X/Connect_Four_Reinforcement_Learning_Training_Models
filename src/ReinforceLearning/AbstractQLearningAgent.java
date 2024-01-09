package ReinforceLearning;

import dto.Connect4Dto;

import java.util.Random;
interface QLearningAgent<T> {
    void trainAgent(int episodes);
    void testAgent();
    void resetBoard();//connect4
    int[] makeMove(int action);
    int calculateReward();
    boolean isGameOver();//connect4
    void printBoard();//connect4
    int[] getLegalActions();
    int stateToIndex(T state);
    int selectAction();

    // Action selection logic
    int selectAction(int state);

}

public abstract class AbstractQLearningAgent<T> implements QLearningAgent {

    protected T qTable;
    protected T[] environment;

    // Hyperparameters
    protected double learningRate = 0.1;
    protected double discountFactor = 0.9;
    protected double explorationRate = 0.8;
    protected double minExplorationRate = 0.1;
    protected double explorationDecay = 0.95;

    // Constructor
    public AbstractQLearningAgent(T[] environment, T qTable) {
        this.environment = environment;
        this.qTable = qTable;
    }

    // Action selection logic
    @Override
    public int selectAction(int state) {
        // Implement your action selection logic here
        // For example, you can use epsilon-greedy strategy
        Random random = new Random();
        if (random.nextDouble() < explorationRate) {
            // Explore (random action)
            return random.nextInt(environment.length);
        } else {
            // Exploit (choose the best action based on Q-values)
            // Implement the logic to get the best action from the Q-table for the given state
            // This depends on the specifics of your Q-learning algorithm
            return getBestAction(state);
        }
    }

    // Train the agent
    @Override
    public void trainAgent(int episodes) {
        // Implement your training logic here
        for (int episode = 0; episode < episodes; episode++) {
            // Perform one episode of training
            // Update Q-values based on the experience gained during the episode
            // This depends on the specifics of your Q-learning algorithm
            performEpisode();

            // Decay exploration rate
            explorationRate = Math.max(minExplorationRate, explorationRate * explorationDecay);
        }
    }

    // Test the agent
    @Override
    public void testAgent() {
        // Implement your testing logic here
        // Evaluate the performance of the trained agent
    }

    // Method to get the best action for a given state
    protected abstract int getBestAction(int state);

    // Method to perform one episode of training
    protected abstract void performEpisode();
}


