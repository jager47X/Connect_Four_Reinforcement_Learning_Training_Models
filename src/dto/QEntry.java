package dto;

import java.util.*;

public class QEntry {
    private final Map<Integer, Double> qEntry;

    public QEntry(int newAction, double newQValue) {
        this.qEntry = new HashMap<>();
        this.qEntry.put(newAction, newQValue);
    }
    public QEntry() {
        this.qEntry = new HashMap<>();
    }


    public Set<Integer> getAction() {
        return new HashSet<>(this.qEntry.keySet());
    }
    public QEntry updateQEntry(int newAction, double newQValue) {
        Map<Integer, Double> qValues = new HashMap<>(this.qEntry);

        // Update QValue for the new action
        qValues.put(newAction, newQValue);

        // Clear existing QEntry map and put the updated values
        this.qEntry.clear();
        this.qEntry.putAll(qValues);

        return this;
    }


    public void setQValue(int action, double qValue) {
        if(!qEntry.containsKey(action)){
            qEntry.put(action, qValue);
        }
    }

    public double getQValue(int action) {
        return qEntry.getOrDefault(action, 0.0);
    }

    public Map<Integer, Double> getQEntry() {
        return qEntry;
    }
}
