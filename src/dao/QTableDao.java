package dao;

import dto.QTableDto;

import java.util.HashMap;
import java.util.Map;




public class QTableDao extends BaseDao<QTableDto>  {


    private static QTableDao instance;
    public static Map<String, int[]> table= new HashMap<>();

    private QTableDao(Map<String, int[]>  table) {
        super();//import QTable from csv
        QTableDao.table.putAll(table);
    }

    public static QTableDao getInstance(){
        if(instance != null){
            return instance;
        }
        instance = new QTableDao(table);
        return instance;
    }

    public int[] get(String state){
        return table.get(state);
    }//fix to get all


}
