package dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.BaseDao;
import dao.QTableDao;
import GameEnviroment.Connect4;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class QTableDto extends BaseDto {


    public QTableDto() {// default
        this.learningRate = 0.1;
        this.discountFactor = 0.9;
        this.minExplorationRate = 0.1;
        this.explorationDecay = 0.95;
        this.explorationRate=0.9;
        ExportingPolicyNetWork =new HashMap<>();
        ImportedPolicyNetWork =QTableDao.getInstance().getImportedMap();
        QTableDao.getInstance().getImportedMap().clear();

    }
    public QTableDto(QTableDto imported) {//RL
        this.learningRate = imported.learningRate;
        this.discountFactor = imported.discountFactor;
        this.minExplorationRate = imported.minExplorationRate;
        this.explorationDecay = imported.explorationDecay;
        this.explorationRate= imported.explorationRate;
        ExportingPolicyNetWork =imported.ExportingPolicyNetWork;
        ImportedPolicyNetWork =QTableDao.getInstance().getImportedMap();
        QTableDao.getInstance().getImportedMap().clear();
        this.game=new Connect4();
    }
    private Map<String, Set<QEntry>> ExportingPolicyNetWork;
    private Map<String, Set<QEntry>> ImportedPolicyNetWork;

    public boolean hasNextState;

    public Map<String, Set<QEntry>> getImportedPolicyNetWork() {
        return ImportedPolicyNetWork;
    }

    public void setImportedPolicyNetWork(Map<String, Set<QEntry>> importedPolicyNetWork) {
        ImportedPolicyNetWork = importedPolicyNetWork;
    }
    public Map<String, Set<QEntry>> getExportingPolicyNetWork() {
        return ExportingPolicyNetWork;
    }
    public void setExportingPolicyNetWork(Map<String, Set<QEntry>> exportingPolicyNetWork) {
        this.ExportingPolicyNetWork = exportingPolicyNetWork;
    }

    double learningRate;
    double discountFactor;
    double minExplorationRate;
    double explorationDecay;
    double explorationRate;

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double getDiscountFactor() {
        return discountFactor;
    }

    public void setDiscountFactor(double discountFactor) {
        this.discountFactor = discountFactor;
    }

    public double getMinExplorationRate() {
        return minExplorationRate;
    }

    public void setMinExplorationRate(double minExplorationRate) {
        this.minExplorationRate = minExplorationRate;
    }

    public double getExplorationDecay() {
        return explorationDecay;
    }

    public void setExplorationDecay(double explorationDecay) {
        this.explorationDecay = explorationDecay;
    }

    public double getExplorationRate() {
        return explorationRate;
    }

    public void setExplorationRate(double explorationRate) {
        this.explorationRate = explorationRate;
    }

    public Set<Double> getAllRewards(String state, int action) {
        if (QTableDao.getInstance().getImportedMap().containsKey(state)) {
            Set<QEntry> qEntrySet = QTableDao.getInstance().getImportedMap().get(state);

            return qEntrySet.stream()
                    .map(qEntry -> qEntry.getQValue(action))
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet(); // or null
    }
    public synchronized void toQtable(List<String> moves) {

        setHashedData(moves);
        // Create new instances of location and reward lists for each game iteration
        List<Integer> location = new ArrayList<>();
        List<Double> reward = new ArrayList<>();

            for (String move : moves) {//put into List
                location.add(addLocation(move));
                reward.add(addReward(move));
            }

                for (int turn = 0; turn <moves.size()-1; turn++) {//get action and qvalue
                    int action = 0;
                    double qvalue=0.0;
                    if(location.size()>turn){
                        action= location.get(turn+1);
                        qvalue = reward.get(turn+1);
                    }

                    StringBuilder state = new StringBuilder( );
                    for (int index = 0; index < moves.size(); index++) {//add up all action
                        if (index==0) {
                            state.append(0);
                        }else if(index%2==0){
                            state.append(2);
                        }else{
                            state.append(1);
                        }
                        state.append(location.get(index));
                    }

                    //int currentTurn=turn+1;
                  updateQTable(state.toString(),  action, qvalue);
                }
    }

    public synchronized void updateQTable(String state, int action, double qvalue) {
        QEntry qEntry = new QEntry(action, qvalue);

        Set<QEntry> qEntrySet = ImportedPolicyNetWork.computeIfAbsent(state, k -> new HashSet<>());

        // Use iterator to avoid ConcurrentModificationException
        Iterator<QEntry> iterator = qEntrySet.iterator();
        while (iterator.hasNext()) {
            QEntry entry = iterator.next();
            if (entry.getAction().contains(action)) {
                iterator.remove(); // Remove the existing entry
            }
        }

        // Add the new QEntry
        qEntrySet.add(qEntry);
    }



    private  int addLocation(String move) {
        int location=0;
        Pattern pattern = Pattern.compile("P([012])L(\\d+)R(\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(move);
        if (matcher.matches()) {
            location=Integer.parseInt(matcher.group(2));
        }
        return location;
    }
    private  double addReward(String move) {
        double reward=0.0;
        Pattern pattern = Pattern.compile("P([012])L(\\d+)R(\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(move);
        if (matcher.matches()) {
            reward=Double.parseDouble(matcher.group(3));
        }

        return reward;
    }

    public QTableDto converge(QTableDto episode){
        //add the episode to RL Policy Network
        toQtable(episode.getHashedData());

        return this;
    }

    public Set<QEntry> getQEntry(String state) {
        if (!ImportedPolicyNetWork.containsKey(state)) {
            System.out.println("The state is absent on the QTableDao.getInstance().get QTable()");
            // Return a default QEntry indicating the absence of the state
            return Set.of(new QEntry());
        }
        System.out.println("MATCHED FROM CSV:" + state);

        return ImportedPolicyNetWork.get(state);
    }

    public void setQEntry(int action, double newQValue, String state) {
        ImportedPolicyNetWork.computeIfAbsent(state, key -> new HashSet<>()).add(new QEntry(action, newQValue));
    }



    // Get the maximum Q-value for a given state
    public double getMaxQValue(String state) {
        Set<QEntry> qEntrySet = ImportedPolicyNetWork.getOrDefault(state, Set.of(new QEntry()));

        return qEntrySet.stream()
                .mapToDouble(qEntry -> qEntry.getQValue(1)) // Use any valid actionIndex here
                .max()
                .orElse(0.0);
    }


    // Get the Q-value for a state-action pair
    public Map<Integer, Double> getQValues(String state, int action) {
        Set<QEntry> qEntrySet = ImportedPolicyNetWork.computeIfAbsent(state, key -> Collections.emptySet());

        return qEntrySet.stream()
                .filter(qEntry -> !qEntry.getAction().isEmpty())
                .collect(Collectors.toMap(
                        qEntry -> qEntry.getAction().iterator().next(),
                        qEntry -> qEntry.getQValue(action)
                ));
    }

    public boolean isQValueAbsent(String state, int action) {
        Set<QEntry> qEntrySet = ImportedPolicyNetWork.getOrDefault(state, Collections.emptySet());

        return qEntrySet.stream()
                .noneMatch(qEntry -> qEntry.getAction().contains(action));
    }

    public void updateQValue(String state, int action, double updatedQValue) {
        Set<QEntry> qTableEntry = ImportedPolicyNetWork.get(state);

        if (ImportedPolicyNetWork.get(state) != null) {
            // Iterate through the set to find the QEntry with the matching action
            for (QEntry qEntry : qTableEntry) {
                if (qEntry.getQEntry().containsKey(action)) {
                    qEntry.updateQEntry(action,updatedQValue);
                   ImportedPolicyNetWork.get(state).add(qEntry);
                    break; // Exit the loop once the action is found
                }
            }
        } else {
            System.out.println("State " + state + " not present in Q-table");
        }
    }
    public void ToGson(AtomicReference<List<QTableDto>> ExportingData) {

        // Set other fields if needed
        String jsonString = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for (int i = 0; i < ExportingData.get().size(); i++) {
            jsonString = gson.toJson(ExportingData.get().get(i));
        }
        if(jsonString!=null){
            try (Writer writer = new FileWriter("TrainedModel.json")) {
                writer.write(jsonString);
            } catch (IOException e) {
                System.err.println("Error saving QTable to JSON: " + e.getMessage());
            }
        }

    }



    public String getNextState(int currentTurn, String state) {
        int nextTurn = currentTurn + 1;

        List<List<String>> importedGames = BaseDao.getImportedGames();

        for (List<String> importedGame : importedGames) {
            if (importedGame.size() > nextTurn) {
                String currentState = importedGame.get(currentTurn);
                String nextState = importedGame.get(nextTurn);

                if (currentState.contains(state)) {
                    return nextState;
                }
            }
        }
        return "NULL";
    }
}
