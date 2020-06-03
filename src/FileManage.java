import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManage {

    public void saveDataFile(String batchNumber, String finalFruit, String fruitCode, int batchWeight, String date) {
        try {
            Path filepath = Paths.get("C:\\Users\\GA\\Documents\\Year1\\CS112 Programming 1- T3 Project\\step1Output\\" + batchNumber + ".txt");
            java.io.File outputFile = filepath.toFile();
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter writer = new FileWriter(filepath.toString());
            writer.write("Batch Number: " + batchNumber + "\n"
                    + "Recieved Date: " + date + "\n"
                    + "Fruit Type" + finalFruit + "\n"
                    + "Batch Weight: " + batchWeight + "kg" + "\n");
            writer.close();
        } catch (IOException ioe) {
            System.out.println("There was a problem writing to " + batchNumber + ".txt");
            System.out.println("Error message: " + ioe.getMessage());
        }

    }
}
