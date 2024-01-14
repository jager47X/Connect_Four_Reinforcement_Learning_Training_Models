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
    protected List<String> ImportedData=new ArrayList<>();
    protected List<String> HashedData=new ArrayList<>();

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
        this.board=game.getBoard();
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

    public void exportCSV() {
        try (FileWriter writer = new FileWriter(CSV.getPath())) {

            for (int i = 0; i <ImportedData.size() ; i++) {//imported game
                writer.append(ImportedData.get(i));
                if(ImportedData.get(i).contains("-1")){
                    writer.append(",");
                }else{
                    writer.append("\n");
                }
            }

            for (String hashedLine : HashedData) {//current game
                writer.append(hashedLine);
                if(hashedLine.contains("-1")){
                    writer.append(",");
                }else{
                    writer.append("\n");
                }
                // Add a newline after each line
            }
            System.out.println("CSV exported successfully to"+CSV.getPath()+".");
        } catch (IOException e) {
            System.err.println("Error exporting CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void import_CSV() {

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV.getPath()))) {
            String line;
            int gameSet=1;

            while ((line = reader.readLine()) != null) {
                // Process each line as needed
                System.out.println("Processing a CSV line Game:"+gameSet);
                gameSet=processCSVLine(line,gameSet);

            }


            System.out.println("CSV imported successfully from: " + CSV.getPath());
        } catch (IOException e) {
            System.err.println("Error importing CSV: " + e.getMessage());
            e.printStackTrace();
        }


    }



    protected int processCSVLine(String line,int gameSet) {
        gameSet++;
        String[] data =line.split(",");//game 1 2 3
        for (int i = 0; i < data.length; i++) {
            ImportedData.add(i,data[i]);
        }

        System.out.println(Arrays.toString(data));
       return  gameSet;
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

