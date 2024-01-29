package dao;


public class CSV {

    private String fileName;
    private static final String path = ".\\Data\\";
   // private static final String path = "C:\\Users\\yutto\\OneDrive\\Documents\\SFSU\\Projects\\DATABASE\\";

    public CSV(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getModel() {
        return path + fileName;
    }


}
