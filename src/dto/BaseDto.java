package dto;


import dao.CSV;
import target.Connect4;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDto  {

    //id?
    List<String> ImportedData;

    public BaseDto(List<String> ImportedData) {
        this.ImportedData=ImportedData;
    }

    public List<String> getImportedData() {
        return ImportedData;
    }

    public void setImportedData(List<String> importedData) {
        ImportedData = importedData;
    }

    public BaseDto() {
    }

    public abstract  void exportCSV() ;

    public abstract void import_CSV();
}

