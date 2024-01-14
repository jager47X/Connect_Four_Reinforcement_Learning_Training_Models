package dto;

import target.Connect4;

import java.util.Map;

import static dao.QTableDao.table;


public class QTableDto extends BaseDto{



    public QTableDto(Connect4 game) {//CSV to dto
        super(game);
        import_CSV();
        initializeMap();
    }


    private void initializeMap(){
        for (String data : ImportedData) {
            int[] values = new int[ImportedData.size()];
            table.put(data, values);
        }
    }
    public Map<String, int[]> getTable() {
        return table;
    }






}
