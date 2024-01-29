package dao;


import dto.QEntry;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QTableDao extends BaseDao {

    protected static QTableDao instance;
    private final Map<String, Set<QEntry>> importedMap;

    List <List<Integer>> locationList;

    List <List<Double>> rewardList;


    public Map<String, Set<QEntry>> getImportedMap() {
        return importedMap;
    }

    String importingModel ="TEST_Supervised_Learning_policyNetwork.csv";

    public static QTableDao getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new QTableDao();
        return instance;
    }
    public QTableDao() {
        super();
        import_CSV(importingModel);// Import QTable from CSV
        importedMap = new HashMap<>();
        locationList=new ArrayList<>();
        rewardList=new ArrayList<>();
        importSLNetWork();//load SL csv from csv
    }


    public  void importSLNetWork() {
        // Load all into List location and reward
// Load all into List location and reward
        if (ImportedGames != null) {
            for (List<String> moves : ImportedGames) {
                if (moves != null) {
                    // Create new instances of location and reward lists for each game iteration
                    List<Integer> location = new ArrayList<>();
                    List<Double> reward = new ArrayList<>();

                    for (String move : moves) {
                        location.add(addLocation(move));
                        reward.add(addReward(move));
                    }

                    // Add the new instances to locationList and rewardList
                    locationList.add(location);
                    rewardList.add(reward);
                }
            }
        }

        //iterate over
        if (ImportedGames!= null) {

          //  Connect4 connect4;

            for (int gameIndex = 0; gameIndex < ImportedGames.size(); gameIndex++) {
              //  connect4=new Connect4();
                for (int turn = 0; turn <ImportedGames.get(gameIndex).size()-1; turn++) {
                    int action = 0;
                    double qvalue=0.0;
                    if(locationList.get(gameIndex).size()>turn){
                        action= locationList.get(gameIndex).get(turn+1);
                        qvalue = rewardList.get(gameIndex).get(turn+1);
                    }



                    int game=gameIndex+1;
                    int currentTurn=turn+1;
                    System.out.println("game:"+game+" turn:"+currentTurn);


                    StringBuilder state = new StringBuilder( );
                    for (int index = 0; index < turn+1; index++) {//add up all action
                        if (index==0) {
                            state.append(0);
                        }else if(index%2==0){
                            state.append(2);
                        }else{
                            state.append(1);
                        }
                        state.append(locationList.get(gameIndex).get(index));
                    }

                    System.out.println("Qtable:Saving state:"+state);
                    System.out.println("Qtable:Saving action:"+action+", reward:"+ qvalue);
                    updateQTable(state.toString(),  action, qvalue);
                }

            }
        }
    }
    public  void updateQTable(String state, int action, double qvalue) {
        QEntry qEntry = new QEntry(action, qvalue);

        Set<QEntry> qEntrySet = importedMap.computeIfAbsent(state, k -> new HashSet<>());

        // Check if an entry with the same action already exists
        boolean containsAction = qEntrySet.stream()
                .anyMatch(entry -> entry.getAction().contains(action));

        if (containsAction) {
            // Entry with the same action exists, update it if needed
            qEntrySet.forEach(entry -> {
                if (entry.getAction().contains(action)) {
                    entry.setQValue(action, qvalue);
                }
            });
        } else {
            // Entry with the same action doesn't exist, add the new QEntry
            qEntrySet.add(qEntry);
        }
    }

    public int addLocation(String move) {
        int location = 0;
        Pattern pattern = Pattern.compile("P([012])L(\\d+)R(\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(move);
        if (matcher.matches()) {
            location = Integer.parseInt(matcher.group(2));
        }
        return location;
    }
    public   double addReward(String move) {
       double reward=0.0;
        Pattern pattern = Pattern.compile("P([012])L(\\d+)R(\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(move);
        if (matcher.matches()) {
            reward=Double.parseDouble(matcher.group(3));
        }

        return reward;
    }


}
