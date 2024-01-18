package dao;

import dto.QTableDto;

import java.util.HashMap;
import java.util.Map;




public class QTableDao extends BaseDao<QTableDto>  {

    int size;

    public int getSize() {
        return size;
    }

    private static QTableDao instance;
    public static Map<String, Integer> table= new HashMap<>();

    private QTableDao() {
        super();//import QTable from csv
        this.size=0;
        initializeMap();
    }
    private void initializeMap() {

        //split reward
        if (ImportedData != null) {
            for (String importedDatum : ImportedData) {
                size++;
                String[] parsed = importedDatum.split("R");
                System.out.println("stateIndex:" + parsed);
                Integer reward = Integer.parseInt(parsed[1]);
                String state = parsed[0];
                table.put(state, reward);
            }
        }
    }
    public static QTableDao getInstance(){
        if(instance != null){
            return instance;
        }
        instance = new QTableDao();
        return instance;
    }

    public Integer get(String state){
        return table.get(state);
    }//fix to get all


}
