package dao;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QTableDao extends BaseDao {

    private static QTableDao instance;
    private final Map<String, QEntry> table = new HashMap<>();
    int player;
    List<Integer> location=new ArrayList<>();
    int winner;
    int reward ;

    private QTableDao() {
        super();
        import_CSV();// Import QTable from CSV
        initializeMap();
    }

    private void initializeMap() {
        String initialState="00";
        table.put(initialState, new QEntry(0,0));
        if (ImportedData != null) {
            int turn=0;
            int game=1;

            for (String importedMove : ImportedData) {
                if(importedMove==null){
                    break;
                }
                turn++;
                int action = -1;
                StringBuilder state = new StringBuilder( );
                System.out.println("game:"+game+" turn:"+turn);
                    parseMove(importedMove);
                    action= location.get(location.size()-1);//put the current location(action)
                for (int i = 0; i < turn; i++) {//add up all action
                    if (i % 2==0) {
                        state.append(1);
                    }else{
                        state.append(2);
                    }
                    state.append(location.get(i));
                }
                System.out.println("state:"+state);
                System.out.println("action:"+action+"reward:"+reward);
                    table.put(state.toString(), new QEntry(action,reward));

                if(winner!=-1){//last move
                    turn=0;
                    location.clear();
                    game++;
                }
            }
        }
    }

    public static QTableDao getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new QTableDao();
        return instance;
    }

    private  void parseMove(String move) {
        Pattern pattern = Pattern.compile("P([12])L(\\d+)W(-?\\d)R(\\d+)");
        Matcher matcher = pattern.matcher(move);

        if (matcher.matches()) {
            this.player = Integer.parseInt(matcher.group(1));
            this.location.add(Integer.parseInt(matcher.group(2)));
            this.winner = Integer.parseInt(matcher.group(3));
            this.reward = Integer.parseInt(matcher.group(4));
            System.out.println("Player: " + this.player + ", Location: " + this.location + ", Movement: " + this.winner + ", Reward: " + this.reward);
        }
    }



    public QEntry getQvalue(String state) {
        if(Objects.equals(state, "00")){
            //insert the first move
            System.out.println("first move");
            return new QEntry(0, 0); //indicate the first state
        }
        if(!table.containsKey(state)){
            System.out.println("The state is absence on the Qtable");
            // Return a default QEntry indicating the absence of the state
            return new QEntry(-1, -1); // default values for null
        }

        QEntry qEntry = table.get(state);
        System.out.println("state: "+state);
        return qEntry;



    }

    public int getTableSize() {
        return table.size();
    }
}
