## Table of Contents
1. [Introduction](#1-introduction)
   - [Background](#background)
   - [Objective](#objective)
2. [Connect Four Game](#2-connect-four-game)
   - [Game Rules](#game-rules)
   - [Game Environment](#game-environment)
3. [Supervised Learning](#3-supervised-learning)
   - [Overview](#overview)
   - [Role of Rule-Based AI in Supervised Learning](#role-of-rule-based-ai-in-supervised-learning)
4. [Reinforcement Learning](#4-reinforcement-learning)
   - [Overview](#overview-1)
   - [Q-Learning and Bellman's Equation](#q-learning-and-bellmans-equation)
   - [Exploration-Exploitation Strategy](#exploration-exploitation-strategy)
5. [Project Implementation](#5-project-implementation)
   - [Environment Implementation](#environment-implementation)
   - [Board Class](#board-class)
   - [Tile Class](#tile-class)
   - [Connect4 Class](#connect4-class)
   - [State Representation](#state-representation)
   - [RuleBased AI](#rulebased-ai)
   - [Q-Table Data Structure](#q-table-data-structure)
   - [Dao Class - BaseDao (using Singleton)](#dao-class---basedao-using-singleton)
   - [CSV QTableDao](#csv-qtabledao)
   - [Dto Class - BaseDto](#dto-class---basedto)
   - [Connect4Dto](#connect4dto)
   - [QEntry Class](#qentry-class)
   - [AbstractReinforceLearningAgent2D Class](#abstractreinforcelearningagent2d-class)
   - [ReinforceLearningAgentConnectFour Class](#reinforcelearningagentconnectfour-class)
6. [Training Process](#6-training-process)
7. [Results and Evaluation](#7-results-and-evaluation)
   - [Training Performance](#training-performance)
   - [Exploration Rate](#exploration-rate)
8. [Challenges and Solutions](#8-challenges-and-solutions)
   - [Performance Issue](#performance-issue)
   - [Understanding how Q Learning works and implementing the Data Structure](#understanding-how-q-learning-works-and-implementing-the-data-structure)
   - [Freezing Issue](#freezing-issue)
   - [Updating QEntry](#updating-qentry)
   - [Concurrent Issue](#concurrent-issue)
9. [Future Improvements](#9-future-improvements)
   - [Approach to Thread Loss during Training](#approach-to-thread-loss-during-training)
   - [Integrating the trained Connect Four reinforcement learning model into a game app](#integrating-the-trained-connect-four-reinforcement-learning-model-into-a-game-app)
10. [Conclusion](#10-conclusion)
11. [References](#11-references)
12. [CopyLicense](#12-copylicense)
    - [Train with Supervised Learning (300k)](#train-with-supervised-learning-300k)
    - [Train with Reinforcement Learning (200k) based on Supervised Learning (300k) data](#train-with-reinforcement-learning-200k-based-on-supervised-learning-300k-data)
    - [Train based on Reinforced Learning (200k) data](#train-based-on-reinforced-learning-200k-data)
    - [Export to CSV](#export-to-csv)
7. [Results and Evaluation](#7-results-and-evaluation)
    - [Training Performance](#training-performance)
    - [Optimization Efforts](#optimization-efforts)
    - [Exploration Rate](#exploration-rate)
8. [Challenges and Solutions](#8-challenges-and-solutions)
    - [Performance issue](#performance-issue)
    - [Understanding how Q Learning works and implementing the Data Structure](#understanding-how-q-learning-works-and-implementing-the-data-structure)
    - [Freezing issue](#freezing-issue)
    - [Concurrent Issue](#concurrent-issue)
9. [Future Improvements](#9-future-improvements)
    - [Approach to Thread Loss during Training](#approach-to-thread-loss-during-training)
    - [Integrating the trained Connect Four reinforcement learning model into a game app](#integrating-the-trained-connect-four-reinforcement-learning-model-into-a-game-app)
10. [Conclusion](#10-conclusion)
11. [References](#11-references)
12. [CopyLicense](#12-copylicense)

## 1. Introduction
### Background
In 2020, I developed a Connect4 game in C++. While exploring AI algorithms, I encountered sources detailing an agent learning to walk. The goal was to teach the AI to walk straight, and after numerous attempts, the agent successfully achieved the objective. This analogy drew parallels with how humans learn to walk as infants.
Intrigued by this concept, I delved into C++ coding in 2021. Initially, I implemented a similar algorithm, replacing Bellman's equation with a "winner" variable as a flag. I ran random inputs in Connect 4, tracking the winning player for each game. However, I abandoned this approach due to inefficiency and excessive memory usage. Although the agent identified placing the chip in the middle on the first turn as the most winning pattern, exploring all possible paths consumed too much memory, leading to shortages due to my limited C++ programming skills at that time.
This experience motivated me to undertake a new project focused on refining the reinforcement learning aspect. This time, I adopted Q-Learning and designed algorithms based on thorough research. Using Java proved to be more efficient in terms of memory utilization and speed compared to the unoptimized C++ code.
Objective
### Objective
Implementing reinforcement learning with Bellman's equation in Connect4 involves creating an agent that learns to play connect 4 by interacting with the environment, updating its strategies based on received rewards. Bellman's equation, a fundamental concept in reinforcement learning, helps guide the learning process by updating the expected cumulative rewards associated with different actions in various states.

## 2. Connect Four Game
### Game Rules
Connect Four is a two-player connection game in which the players first choose a color and then take turns dropping one of their colored discs from the top into a vertically suspended grid. The grid has six rows and seven columns. The objective of the game is to connect four of one's own discs of the same color in a row, horizontally, vertically, or diagonally, before the opponent does. The game begins with an empty grid, and players take turns dropping a disc of their chosen color into any of the seven columns. The disc then falls to the lowest available position within the selected column. The game continues with players alternating turns until one of the players successfully forms a horizontal, vertical, or diagonal line of four discs of their color. If the entire grid is filled without a player achieving a Connect Four, the game is a draw.

### Game Environment
The Connect Four game environment is implemented as a class that manages the game state and rules. The game state is represented as a 6x7 grid, where each cell can be empty, filled with a player's disc (either red or yellow), or out of bounds.
3. Supervised Learning

## 3. Supervised Learning
### Overview
Supervised Learning is a machine learning paradigm where a model is trained on a labeled dataset, meaning the algorithm learns from examples where the correct output is provided for each input. The goal is for the model to generalize its learning to make accurate predictions or classifications on new, unseen data. In supervised learning, the training dataset serves as a teacher, guiding the model to learn the mapping between input features and their corresponding labels.

### Role of Rule-Based AI in Supervised Learning
In the supervised learning phase of Connect Four, the Rule-Based AI plays...

## 4. Reinforcement Learning
### Overview
Reinforcement Learning (RL) is a machine learning paradigm where an agent learns to make decisions by interacting with an environment. The agent receives feedback in the form of rewards or punishments based on the actions it takes, and its goal is to learn a policy that maximizes the cumulative reward over time. RL is well-suited for problems where an agent needs to make a sequence of decisions in a dynamic environment, adapting its strategy through trial and error. 

### Q-Learning and Bellman's Equation
Q-Learning is a model-free reinforcement learning algorithm designed to acquire the optimal action-value function, represented by Q-values. These values serve to estimate the expected cumulative reward associated with taking a specific action in a given state and subsequently following the optimal policy. In the context of Connect Four, six essential elements are pivotal:
Agents: The agent refers to the entity, such as a player, that acts and operates within the environment, making decisions based on learned strategies.
States: States are variables identifying the current position of the agent in the environment. In Connect Four, this corresponds to the current configuration of the game board.
Actions: Actions represent the operations the agent can undertake when in a specific state. In Connect Four, these are the individual moves made by the player during the game.
Rewards: Rewards are fundamental in reinforcement learning, providing positive or negative responses to the agent's actions. In Connect Four, rewards are associated with each move made by the player in the game.
Episodes: An episode concludes when an agent can no longer take new actions and terminates. In Connect Four, episodes correspond to completed games, capturing the entire sequence of moves until a game concludes.
Q-Values: Q-values serve as metrics measuring the merit of an action in a specific state. In the context of Connect Four, Q-values represent the learned estimates across multiple games, guiding the agent's decision-making process.
Bellman's equation is a key concept in Q-Learning, expressing the relationship between the current Q-value, the immediate reward, and the maximum Q-value of the next state. It is formalized as:
Q(s,a) = Q(s,a) + α * (r + γ * max(Q(s',a')) - Q(s,a))
The equation breaks down as follows:
Q(s, a) represents the expected reward for taking action a in state s.
The actual reward received for that action is referenced by r while s' refers to the next state.
The learning rate is α and γ is the discount factor.
The highest expected reward for all possible actions a' in state s' is represented by max(Q(s', a')).
### Exploration-Exploitation Strategy
The exploration-exploitation trade-off is a fundamental challenge in RL...
The exploration-exploitation trade-off is a fundamental challenge in RL. Exploration involves trying new actions to discover their effects, while exploitation involves choosing actions that are known to yield high rewards based on the learned policy. The exploration-exploitation dilemma is crucial for balancing the need to gather information about the environment and exploiting the current knowledge to maximize rewards.
In Connect Four, handling the exploration-exploitation trade-off involves determining how often the agent should make random moves (exploration) versus choosing the best-known moves based on its current policy (exploitation). A common strategy is epsilon-greedy, where with probabilityϵ, the agent explores by selecting a random action, and with probability1−ϵ, it exploits by choosing the action with the highest Q-value.
To implement this in the model, setting an exploration rate (ϵ) that gradually decreases over time. This allows the agent to explore more in the early stages of learning and shift towards exploitation as it becomes more confident in its learned policy. Fine-tuning the exploration rate is essential for achieving a balance between exploration and exploitation in the context of Connect Four.
## 5. Project Implementation
### Environment Implementation
#### Board Class
Board represents the game board, which is a 2D array of Tile objects.
It includes methods for initializing the board, retrieving and setting the value of tiles, and managing the game state.

#### Tile Class
Tile represents an individual cell on the game board.
Each tile has a value attribute that can be set to either 'X' (for Player 1), 'O' (for Player 2), or '_' (for an empty cell).
The turn attribute is used to keep track of the turn in which a tile was set.

#### Connect4 Class
Connect4 is the main class that orchestrates the game and interacts with the board.
It contains methods for player actions, such as dropping a disc into a column, checking if a column is valid, and updating the game state.
The class maintains information about the active player, the current turn, the winner, and rewards for both players.
There are methods for checking if the game has a winner, if the board is full, and for displaying the current state of the board.
The calculateReward method determines the reward for each player based on the connections formed on the board.
The game's state is represented through the current configuration of the board, and actions involve placing discs in specific columns.

#### State Representation
The state of the Connect Four game is represented by the arrangement of 'X' and 'O' on the game board.
The 2D array of Tile objects in the Board class keeps track of the current state.
Available Actions:

The primary action in Connect Four is for a player to drop a disc into a specific column.
The playerDrop method in the Connect4 class handles the player's action of selecting a column to drop their disc.
The isValidColumn method checks if a selected column is a valid move, and the checkConnection method determines if a winning connection is formed after each move.
### RuleBased AI
The RuleBasedAI class in the provided code implements a deterministic strategy for making moves in Connect Four. This strategy relies on predefined rules, including checking for winning moves, blocking the opponent, and making random moves when necessary. While this approach does not involve learning from experience, it provides a baseline for gameplay.

#### Q-Table Data Structure
The Q-table is a data structure that stores Q-values for state-action pairs in the context of reinforcement learning.
For Connect Four, the state corresponds to the current board configuration, and actions are the possible column choices for the next move.
The Q-values in the table represent the expected cumulative rewards for taking a specific action in a particular state.

#### Dao Class - BaseDao (using Singleton)
The BaseDao class is a Data Access Object that provides an interface for accessing the underlying data storage (e.g., file, database).
It is implemented as a singleton to ensure a single instance across the application for efficient resource usage and consistency.

#### CSV QTableDao
The QTableDao class is responsible for managing the Q-table data and persisting it to a CSV (Comma-Separated Values) file.
It utilizes the BaseDao singleton to handle file operations and implements methods for saving and loading the Q-table from/to the CSV file.

#### Dto Class - BaseDto
The BaseDto class serves as a base Data Transfer Object for communication between different components of the system.
It may include common attributes and methods applicable to various DTOs in the project.

#### Connect4Dto
Connect4Dto is a specific Data Transfer Object representing the state of the Connect Four game. It is used for communication between different parts of the system.

#### QEntry Class
The QEntry class represents entries in the Q-table.
It contains attributes like state, action, and Q-value, encapsulating the information needed to update and retrieve Q-values during the learning process.
AbstractReinforceLearningAgent2D Class
This abstract class serves as a base for reinforcement learning agents in a 2D environment.
It contains environment-specific variables, such as the Connect4 game, the Q-table, and dimensions (ROWS, COLS, ACTIONS).
Constructors initialize the environment, Q-table, and other variables.
The selectAction method implements the logic for action selection during exploration and exploitation phases.
The getLegalActions method returns an array of legal actions that the agent can take.
The updateQValue method updates the Q-value for a given state-action pair.
ReinforceLearningAgentConnectFour Class:
This class extends AbstractReinforceLearningAgent2D and specializes it for the Connect Four game.
It includes methods for supervised learning (SupervisedLearning) and reinforcement learning (ReinforceLearning).
The SupervisedLearning method simulates games with a rule-based AI to generate training data.
The ReinforceLearning method implements the Q-learning algorithm, updating the Q-table based on the agent's interactions with the environment.
The isTerminateState method checks if the game has reached a terminal state.
10.Supervised_Learning and Reinforce_Learning Classes:
These classes implement the Callable interface, allowing them to be executed concurrently.
The train method initiates training for the respective learning type.
The call method is part of the Callable interface and is executed by each thread.
The main method demonstrates the usage of multiple threads for concurrent training.
Note:
The code uses a multi-threaded approach to speed up the training process, with separate threads handling supervised learning and reinforcement learning.
The QTableDto class is utilized to store and manage the Q-table data.
There are methods for exporting the Q-table to a CSV file after training.
#### AbstractReinforceLearningAgent2D Class
●This abstract class serves as a base for reinforcement learning agents in a 2D
environment.
● It contains environment-specific variables, such as the Connect4 game, the Q-table, and
dimensions (ROWS, COLS, ACTIONS).
● Constructors initialize the environment, Q-table, and other variables.
● The selectAction method implements the logic for action selection during exploration and
exploitation phases.
● The getLegalActions method returns an array of legal actions that the agent can take.
● The updateQValue method updates the Q-value for a given state-action pair

#### ReinforceLearningAgentConnectFour Class
● This class extends AbstractReinforceLearningAgent2D and specializes it for the Connect Four
game.
● It includes methods for supervised learning (SupervisedLearning) and reinforcement
learning (ReinforceLearning).
● The SupervisedLearning method simulates games with a rule-based AI to generate training
data.
● The ReinforceLearning method implements the Q-learning algorithm, updating the Q-table
based on the agent's interactions with the environment.
● The isTerminateState method checks if the game has reached a terminal stat
#### Supervised_Learning and Reinforce_Learning Classes
● These classes implement the Callable interface, allowing them to be executed
concurrently.
● The train method initiates training for the respective learning type.
● The call method is part of the Callable interface and is executed by each thread.
● The main method demonstrates the usage of multiple threads for concurrent training.
Note:
● The code uses a multi-threaded approach to speed up the training process, with separate
threads handling supervised learning and reinforcement learning.
● The QTableDto class is utilized to store and manage the Q-table data.
● There are methods for exporting the Q-table to a CSV file after training.

## 6. Training Process
### Set File Path on CSV
The file path for CSV is set to specify the location...

### Import from CSV
The QTableDao class handles the import process, optimizing the existing data...

### Train
a. **Train with Supervised Learning (300k):**
The agent undergoes a supervised learning phase using a Rule-Based AI as a teacher. The teacher provides optimal moves based on predefined rules, and the agent learns from these examples. The Q-table is updated using the obtained knowledge during the supervised learning phase.

b. **Train with Reinforcement Learning (200k) based on Supervised Learning (300k) data:**
The agent now engages in reinforcement learning, building on the knowledge gained from the supervised learning phase. It interacts with the environment, makes moves, and updates the Q-table based on the rewards received. This phase helps the agent learn from its own experiences and improves its decision-making.

c. **Train based on Reinforced Learning (200k) data:**
The agent continues the reinforcement learning process, refining its strategy based on the experiences gained during the previous steps. It explores the environment, takes actions, and updates the Q-table to better estimate the expected cumulative rewards.

### Export to CSV
Once the training process is completed, the Q-table is exported to the CSV file.This step ensures that the updated Q-values and learned knowledge are saved for future use without the need to retrain from scratch.
Note:
The numbers (300k and 200k) in the training steps represent the number of interactions or episodes during each training phase. The actual numbers may vary based on experimentation and the specific requirements of the reinforcement learning algorithm.
The training process involves a combination of supervised learning and reinforcement learning, allowing the agent to learn from both expert guidance and its own experiences in the environment.

## 7. Results and Evaluation
### Training Performance
#### Optimization Efforts
Highlight any optimization efforts implemented during the project to address performance issues.
Discuss the impact of optimizations on training speed and overall efficiency.

#### Exploration Rate
The initial exploration rate is established at 0.9, and it progressively decreases through the exploration decay rate of 0.95. This approach ensures a gradual reduction in the agent's inclination towards exploration over time, allowing for a balanced transition from exploration to exploitation as the learning process unfolds.

## 8. Challenges and Solutions
### Performance issue
Encountering initial performance issues, specifically slow execution speed, presented a significant challenge during the project. To address this, a systematic optimization process was undertaken. This involved iteratively addressing bugs within loops, introducing multithreading to enhance parallelism, and resolving synchronization errors through the strategic use of Thread.sleep(). These adjustments were instrumental in achieving synchronized and optimized execution, ultimately overcoming the initial speed constraints in the project.

### Understanding how Q Learning works and implementing the Data Structure
Navigating the intricacies of Q-learning and subsequently implementing it in Java posed a significant intellectual challenge. Initially uncertain about the optimal data structure for the Q-table and the appropriate placement of rewards within it, I adopted a structured approach. Upon gaining a comprehensive understanding of Q-learning, I opted for a Map and Set-based data structure. Specifically, I utilized a Map, where each State corresponds to a Set of QEntry instances. Within each QEntry, actions are mapped to their respective Q-values. This systematic representation ensures that the Q-table effectively captures all potential Q-values for a given state.

### Freezing issue
To tackle freezing issues encountered during performance enhancements, I implemented a CPU monitor to investigate potential CPU overload. Upon discovering a significantly high CPU usage, I introduced a time exception handler set to 5 seconds. This approach aimed to prevent prolonged execution times and ensure smoother performance by allowing controlled pauses in the processing, addressing the freezing problem associated with excessive CPU workload.

### Concurrent Issue
The project faced concurrency-related challenges, including race conditions and deadlocks, which could result in unpredictable behavior.
Solution: Implemented a lock (agentLock) to ensure that only one thread can modify the ImportedPolicyNetWork map at a time. The use of agentLock.lock() and agentLock.unlock() methods prevents concurrent modifications and eliminates ConcurrentModificationException. The agentLock is defined at the class level, ensuring accessibility across methods and threads. Exception handling is applied to release the lock in a finally block, ensuring it is always released, even in case of an exception.

## 9. Future Improvements
### Approach to Thread Loss during Training
#### Identify Bottlenecks
Identify Bottlenecks: Analyze the training process to identify potential bottlenecks that may be causing thread loss. This could be related to resource constraints, data loading, or computational inefficiencies.

#### Optimize Code
Ensure that your code is optimized for parallel processing. This may involve using efficient data loading techniques, optimizing model architecture, and leveraging parallel computing capabilities.

#### Monitoring and Logging
Implement robust monitoring and logging mechanisms to track the progress of each thread during training. This can help identify specific points of failure and diagnose issues.

#### Concurrency Control
Concurrency Control: Check if there are any critical sections in your code that might lead to contention among threads. Implement concurrency control mechanisms such as locks or semaphores to manage access to shared resources.
Integrating the trained Connect Four reinforcement learning model into a game app
### Integrating the trained Connect Four reinforcement learning model into a game app
Integrating the trained Connect Four reinforcement learning model into a game app via a server involves deploying the model on a server-side infrastructure. This enables the game app to communicate with the server, where the reinforcement learning model processes and responds to game-related queries. The server acts as the intermediary, handling the interactions between the game app and the trained model, thereby providing a seamless and responsive gaming experience for users. This integration facilitates real-time decision-making based on the learned policies of the reinforcement learning model, enhancing the overall gameplay.
## 10. Conclusion
In conclusion, this project successfully implemented a Connect Four reinforcement learning model using Q-learning in Java. The model leverages the Q-values to estimate the expected cumulative rewards for different actions in various states of the game. The project incorporated the e-greedy exploration strategy, allowing the model to balance exploration and exploitation during training. Challenges encountered included slow performance, freezing issues, and concurrent problems, which were addressed through optimizations, CPU monitoring, and the introduction of sleep mechanisms. The integration of Rule-Based AI in the supervised learning phase added diversity to the training dataset, enhancing the model's ability to adapt to different game scenarios. Future improvements could involve incorporating the trained model into a Connect Four game app via a server, allowing users to play against the AI. Overall, the project provides insights into the complexities of implementing Q-learning in a gaming environment and highlights the importance of addressing performance and concurrency issues for a robust reinforcement learning system.

## 11. References
- MIT 6.S191: Reinforcement Learning
- What is QLeanring -techtarget.com?
- "Reinforcement Learning: An Introduction" by Richard S. Sutton and Andrew G. Barto
- "Deep Reinforcement Learning" by Pieter Abbeel and John Schulman
- Sutton, R. S., & Barto, A. G. (1998). "Reinforcement learning with replacing eligibility traces." Journal of Machine Learning Research, 1, 123-158.
- "A survey of exploration and exploitation in reinforcement learning" by Matteo Pirotta, Marcello Restelli, and Alessandro Lazaric.
- "Multi-armed Bandit Algorithms and Empirical Evaluation" by Alekh Agarwal.

## 12. CopyLicense
CopyLicense Agreement:
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
This CopyLicense is applicable to both the source code and documentation associated with the Connect Four Reinforcement Learning project.
https://docs.google.com/document/d/1CRJsMWDidAfXju1lHSiluD1p47JT3L3q5oSd74zSm54/edit?usp=sharing
