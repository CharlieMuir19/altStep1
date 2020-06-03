import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManage {
    private String batchNumber;

    public void saveDataFile(String filename) {
        batchNumber = filename;
        Batch batch = new Batch();
        try {
            Path filepath = Paths.get("C:\\Users\\GA\\Documents\\Year1\\CS112 Programming 1- T3 Project\\step1Output\\" + batchNumber + ".txt");
            java.io.File outputFile = filepath.toFile();
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter writer = new FileWriter(filepath.toString());
            writer.write(batch.batchDetails());
            writer.close();
            batch.askPrintDetails();
        } catch (IOException ioe) {
            System.out.println("There was a problem writing to " + batchNumber);
            System.out.println("Error message: " + ioe.getMessage());
        }

    }
}
