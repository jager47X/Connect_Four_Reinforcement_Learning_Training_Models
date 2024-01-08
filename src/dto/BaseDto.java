package dto;


import target.Connect4;

import java.util.List;

public abstract class BaseDto<Data extends Comparable<Data>> {
    private Data data;
   // abstract List<Data> query(Data filter);
    public BaseDto(Data csv) {
        this.data = csv;
    }
    public BaseDto(Connect4 board) {

    }
    public Data getData() {
        return data;
    }

    public void compareWith(BaseDto<Data> other) {
        int result = this.data.compareTo(other.data);
        System.out.println("Comparison result: " + result);
    }

    public abstract void exportCSV();

    public abstract Data import_CSV();
}

