package dto;

import target.Connect4;

import java.util.Map;

import static dao.QTableDao.table;


public class QTableDto extends BaseDto{
    public QTableDto(Connect4 game) {//CSV to dto
        super(game);
    }
}
