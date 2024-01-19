package dao;

import java.util.HashMap;
import java.util.Map;

public class QEntry {
    private final Map<Integer, Integer> qvalue = new HashMap<>();

    public QEntry(int action,int reward) {
        qvalue.put(action,reward);
    }

    public Integer getReward(int action) {
        Integer reward = qvalue.get(action);

        // Return a default value or handle the case when the action is not present
        return (reward != null) ? reward : -1; // You can choose an appropriate default value
    }


}
