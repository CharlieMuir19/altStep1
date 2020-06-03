import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Batch {

    private String date;
    private String farmCode;
    private String finalFruit;
    private String fruitCode;
    private int batchWeight;
    private String batchNumber;

    //begins gathering information from user
    public Batch() {
        printWelcome();
        startChoice();
        generateDate();
    }

    private void printWelcome() {
        System.out.print("Welcome to Renfrewshire Soft Fruits Co batch system.");
        System.out.print("This system allows you to create batches of fruit.");
        System.out.println("Please select a function.");
        System.out.println("1. Create a new batch");
        System.out.println("2. Quit");
    }

    private void startChoice() {
        int createBatch = 1;
        int quit = 2;
        int startChoice = Parser.decide(createBatch, quit);
        if (startChoice == 2){
            System.exit(0);
        }
    }

    //The date is generated and stored to constructor
    public void generateDate() {
        DateTimeFormatter dateformat;
        dateformat = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDateTime now = LocalDateTime.now();
        date = (dateformat.format(now));
        displayDate(date);
    }

    public void displayDate(String date) {
        System.out.println("Date: " + date);
        this.date = date;
        askFarmCode();
    }

    public void askFarmCode() {
        System.out.print("\nEnter Farm Number (000 to 999)");
        System.out.print("\n >");
        int minFarmCode = 0;
        int maxFarmCode = 999;
        int temp = Parser.decide(minFarmCode, maxFarmCode);
        farmCode = String.format("%03d", temp);
        printFarmCode();
    }

    private void printFarmCode() {
        System.out.println("Farm Code: " + farmCode);
        askFruitType();
    }

    private void askFruitType() {
        System.out.println("Choose a fruit: " +
                "\n1. Strawberry " +
                "\n2. Raspberry " +
                "\n3. Blackberry " +
                "\n4. Gooseberry " +
                "\n >");

        int choice = Parser.decide(1,4);
        switch (choice) {
            case 1:
                setSTFruit();
                break;
            case 2:
                setRAFruit();
                break;
            case 3:
                setBLFruit();
                break;
            case 4:
                setGOFruit();
                break;
        }
        askBatchWeight();
    }

    private void askBatchWeight() {
        System.out.println("\nEnter Batch Weight in KGs(max weight per batch is 100kg)" + "\n >");
        batchWeight = Parser.decide(1, 100);
        System.out.println("Batch Weight :" + batchWeight);
        validateBatch();
    }

    private void validateBatch() {
        System.out.println("This batch contains " + batchWeight + " kgs of " +  finalFruit +
                " \nfrom Farm Number " + farmCode + " recieved on " + date + ". Is this correct?");
        System.out.println("\n1. YES");
        System.out.println("\n2. NO");
        int correctBatch = Parser.decide(1,2);
        if (correctBatch == 2){
            generateDate();
        } else if (correctBatch == 1){
            askSave();
        }
    }

    private void askSave() {
        System.out.println("Would you like to save these details?");
        System.out.println("\n1. YES");
        System.out.println("\n2. NO - FINISH");
        int correctBatch = Parser.decide(1,2);
        if (correctBatch == 2){
            new Batch();
        } else if (correctBatch == 1){
            generateBatchNumber();
        }
    }

    private void generateBatchNumber() {
        batchNumber = date + "-" + fruitCode + "-" + farmCode;
        //saveDataFile(batchNumber);
    }

//    private void saveDataFile(String filename) {
//        try {
//            Path filepath = Paths.get("C:\\Users\\GA\\Documents\\Year1\\CS112 Programming 1- T3 Project\\step1Output\\" + batchNumber + ".txt");
//            File outputFile = filepath.toFile();
//            if (!outputFile.exists()) {
//                outputFile.createNewFile();
//            }
//            FileWriter writer = new FileWriter(filepath.toString());
//            writer.write(batchDetails());
//            writer.close();
//            askPrintDetails();
//        } catch (IOException ioe) {
//            dataDisplay.append("There was a problem writing to " + filename);
//            dataDisplay.append("Error message: " + ioe.getMessage());
//        }
//
//    }

    private void setSTFruit() {
        finalFruit = "Strawberry";
        fruitCode = "ST";
        System.out.println("You choose: " + finalFruit);
    }
    private void setRAFruit() {
        finalFruit = "Raspberry";
        fruitCode = "RA";
        System.out.println("You choose: " + finalFruit);
    }
    private void setBLFruit() {
        finalFruit = "Blackberry";
        fruitCode = "BL";
        System.out.println("You choose: " + finalFruit);
    }
    private void setGOFruit(){
        finalFruit = "Gooseberry";
        fruitCode = "GO";
        System.out.println("You choose: " + finalFruit);
    }
}
