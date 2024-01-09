package dto;

import dao.CSV;
import target.Connect4;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
public class Connect4Dto extends BaseDto{
    Connect4 board;

    String player,location,winner;
    private final int CAPACITY = 42;
    List<String> HashedData=new ArrayList<>(CAPACITY);
    List<String> ImportedData=new ArrayList<>(CAPACITY);



    public Connect4Dto(Connect4 board) {
        super();//do nothing
        this.board=board;
    }
    public Connect4 getBoard() {
        return board;
    }

    public void setBoard(Connect4 board) {
        this.board = board;
    }


    public void addLine (int index){
        System.out.println("Total Size: "+ HashedData.size());
    index--;
    HashedData.add(index,hashing());
        System.out.println("Total Line:");
        for (String Line: HashedData) {
            System.out.println(Line);
        }
    }

    
    @Override
    public void exportCSV() {
        try (FileWriter writer = new FileWriter(CSV.getPath())) {

            for (int i = 0; i <ImportedData.size() ; i++) {//imported board
                writer.append(ImportedData.get(i));
                if(ImportedData.get(i).contains("-1")){
                    writer.append(",");
                }else{
                    writer.append("\n");
                }
            }

            for (String hashedLine : HashedData) {//current board
                writer.append(hashedLine);
                if(hashedLine.contains("-1")){
                    writer.append(",");
                }else{
                    writer.append("\n");
                }
                 // Add a newline after each line
            }
            System.out.println("CSV exported successfully.");
        } catch (IOException e) {
            System.err.println("Error exporting CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void import_CSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV.getPath()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Process each line as needed
                processCSVLine(line);
            }
            System.out.println("CSV imported successfully from: " + CSV.getPath());
        } catch (IOException e) {
            System.err.println("Error importing CSV: " + e.getMessage());
            e.printStackTrace();
        }

    }


 
    private void processCSVLine(String line) {
        String[] data =line.split(",");//game 1 2 3
        for (int i = 0; i < data.length; i++) {
            ImportedData.add(i,data[i]);
        }
        System.out.println("Processing a CSV line: ");
        for (String parsed:
             ImportedData) {
            System.out.println(parsed);
        }

    }


    public String hashing() {

        StringBuilder hashcode = new StringBuilder("");

        if (board.getActivePlayer() == board.PLAYER1) player = "1";
        else player = "2";
        hashcode.append("P");
        hashcode.append(player);

        location=Integer.toString(board.getLocation());
        hashcode.append("L");
        hashcode.append(location);

        winner=Integer.toString(board.getWinner());
        hashcode.append("W");
        hashcode.append(winner);

        return hashcode.toString();
    }
}


