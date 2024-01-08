package dao;
import dto.Connect4Dto;
//import the ML target
import target.Connect4;
import java.lang.*;
public class Connect4Dao extends BaseDao<Connect4Dto>{

    Connect4 board;
    public Connect4Dao(Connect4 board) {

        String path=CSV.getPath();
    }


}
