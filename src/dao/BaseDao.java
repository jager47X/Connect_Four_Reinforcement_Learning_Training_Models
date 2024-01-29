package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseDao {



    protected static List<String> ImportedGame =new ArrayList<>();

    protected static List<List<String>>  ImportedGames=new ArrayList<>();

    public static List<List<String>> getImportedGames() {
        if(ImportedGames==null){
            System.out.println("Warning: ImportedData is Null.");
            return new ArrayList<>();
        }
        return ImportedGames;
    }


    public void import_CSV(String fileName) {
        CSV csv=new CSV(fileName);
        System.out.print("Importing the Data....");
        try (BufferedReader reader = new BufferedReader(new FileReader(csv.getModel()))) {
            String line;
            int gameSet=1;

            while ((line = reader.readLine()) != null) {

                // Process each line as needed
               System.out.println("Processing a CSV line Game:"+gameSet);
                gameSet=processCSVLine(line,gameSet);
            }


            System.out.println("SUCCESS : IMPORTING" + csv.getModel());
        } catch (IOException e) {
            System.err.println("Making new File : " +fileName);
        }

    }

    public void exportCSV(List<String>  HashedData,String fileName) {
        CSV csv=new CSV(fileName);
        try (FileWriter writer = new FileWriter(csv.getModel())) {
            int turn=0;
            for (String hashedLine :  HashedData) {//current game
                writer.append(hashedLine);
                if(!(hashedLine.contains("64")||turn>41)){
                    writer.append(",");
                    turn++;
                }else{
                    writer.append("\n");
                    turn=0;
                }
                // Add a newline after each line
            }
            System.out.println("SUCCESS : EXPORTING"+csv.getModel()+".");
        } catch (IOException e) {
            System.err.println("ERROR : " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected int processCSVLine(String line,int gameSet) {
        boolean duplicate = false;
        String[] data =line.split(",");//game 1 2 3
            System.out.println("new game");
            ImportedGame.clear();

        for (int i = 0; i < data.length; i++) {
            ImportedGame.add(i,data[i]);
        }
        for (List<String> existingGame : ImportedGames) {
            duplicate = Arrays.equals(existingGame.toArray(), ImportedGame.toArray());
            if (duplicate) {
                System.out.println("Optimized Duplicate line");
                break; // No need to continue checking if already found a duplicate
            }
        }

        if(!duplicate){
            List<String> importedGame=new ArrayList<>(ImportedGame);
            ImportedGames.add(importedGame);
            System.out.println("data"+Arrays.toString(data));
            gameSet++;
        }


        return  gameSet;
    }


}
