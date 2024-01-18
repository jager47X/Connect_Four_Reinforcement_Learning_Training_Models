package dao;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSV {

public CSV(){

}
    private static final String file = ".\\Data\\connectFour_log.csv";

    public static  String getFile() {
        return file;
    }




}
