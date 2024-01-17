package dto;


import dao.CSV;
import target.Board;
import target.Connect4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseDto {



    private final int CAPACITY = 42;
    protected List<String> HashedData=new ArrayList<>();


    public List<String> getHashedData() {
        return HashedData;
    }

    protected Board board;
    protected Connect4 game;


    public BaseDto(Connect4 game) {
        this.game=game;
        this.board=game.getBoard();
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




    public void addLine(int turn) {
        while(turn>getHashedData().size()){
            turn--;
        }
        getHashedData().add(turn, hashing());
        System.out.println("turn: " + turn );
        System.out.println("Total Size of HashedData: " +getHashedData().size() );
        System.out.println("Total Line:");
        for (String Line : getHashedData()) {
            System.out.println(Line);
        }


    }

    public String hashing() {

        StringBuilder hashcode = new StringBuilder( );

        String player;
        if (game.getActivePlayer() == Connect4.PLAYER1) player = "1";
        else player = "2";
        hashcode.append("P");
        hashcode.append(player);

        String location = Integer.toString(game.getLocation());
        hashcode.append("L");
        hashcode.append(location);

        String winner = Integer.toString(game.getWinner());
        hashcode.append("W");
        hashcode.append(winner);

        return hashcode.toString();
    }
}

