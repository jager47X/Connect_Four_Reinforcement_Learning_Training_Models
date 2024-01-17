package dao;


import dto.BaseDto;
import dto.QTableDto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseDao <T extends BaseDto> {

        public BaseDao( ) {
        }

    protected static List<String> ImportedData=new ArrayList<>();

    public static List<String> getImportedData() {
        if(ImportedData==null){
            System.out.println("Warning: ImportedData is Null.");
            return new ArrayList<>();
        }
        return ImportedData;
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

    public void exportCSV(List<String>  HashedData) {
        try (FileWriter writer = new FileWriter(CSV.getPath())) {

            for (String hashedLine :  HashedData) {//current game
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

    protected int processCSVLine(String line,int gameSet) {
        gameSet++;
        String[] data =line.split(",");//game 1 2 3
        for (int i = 0; i < data.length; i++) {
            ImportedData.add(i,data[i]);
        }

        System.out.println(Arrays.toString(data));
        return  gameSet;
    }


}
