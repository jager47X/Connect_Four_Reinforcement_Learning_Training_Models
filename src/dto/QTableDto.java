package dto;

import dao.BaseDao;
import target.Connect4;

import java.util.Map;

import static dao.QTableDao.table;


public class QTableDto extends BaseDto{



    public QTableDto(Connect4 game) {//CSV to dto
        super(game);
        initializeMap();
    }
    public QTableDto() {//CSV to dto

    }


    private void initializeMap(){
        for (String data : BaseDao.getImportedData()) {
            int[] values = new int[BaseDao.getImportedData().size()];
            table.put(data, values);
        }
    }
    public Map<String, int[]> getTable() {
        return table;
    }






}
