package dto;


import target.Board;
import target.Connect4;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDto {


    private final int CAPACITY = 42;
    protected List<String> ImportedData;
    protected List<String> HashedData;

    public List<String> getImportedData() {
        return ImportedData;
    }

    public List<String> getHashedData() {
        return HashedData;
    }

    public void setHashedData(List<String> hashedData) {
        HashedData = hashedData;
    }

    protected Board board;
    protected Connect4 game;

    public void setImportedData(List<String> importedData) {
        ImportedData = importedData;
    }

    public BaseDto(Connect4 game) {
        this.game=game;
    }
    public BaseDto() {

    }

    public Board getBoard() {
        return game.getBoard();
    }

    public Connect4 getGame() {
        return game;
    }

    public void setGame(Connect4 game) {
        this.game = game;
    }

    public void setBoard(Board board) {
        this.board = board;
    }



}

