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
    Fruit fruit = new Fruit();
    FileManage filemanager = new FileManage();

    //begins gathering information from user
    public Batch() {
        printWelcome();
        generateDate();
        askPrintDetails();
    }

    private void printWelcome() {
        System.out.print("Welcome to Renfrewshire Soft Fruits Co batch system.");
        System.out.print("This system allows you to create batches of fruit.");
        System.out.println("Please select a function.");
        System.out.println("1. Create a new batch");
        System.out.println("2. Quit");
        System.out.println("Please enter 1 or 2");

        startChoice();
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
        int tempFarm = Parser.decide(minFarmCode, maxFarmCode);
        farmCode = String.format("%03d", tempFarm);
        printFarmCode();
    }

    private void printFarmCode() {
        System.out.println("Farm Code: " + farmCode);
        fruit.askFruitType();
        finalFruit = fruit.returnFruit();
        fruitCode = fruit.returnFruitCode();
        askBatchWeight();
    }

    private void askBatchWeight() {
        System.out.println("\nEnter Batch Weight in KGs(max weight per batch is 100kg)" + "\n >");
        batchWeight = Parser.decide(1, 100);
        System.out.println("Batch Weight :" + batchWeight + "kgs");
        validateBatch();
    }

    private void validateBatch() {
        System.out.println("This batch contains " + batchWeight + " kgs of " +  finalFruit +
                " \nfrom Farm Number " + farmCode + " recieved on " + date + ". Is this correct?");
        System.out.println("1. YES - SAVE");
        System.out.println("2. NO - RESTART");
        int correctBatch = Parser.decide(1,2);
        if (correctBatch == 1){
            generateBatchNumber();
        } else if (correctBatch == 2){
            generateDate();
        }
    }

    private void generateBatchNumber() {
        batchNumber = date + "-" + fruitCode + "-" + farmCode;
        filemanager.saveDataFile(batchNumber, finalFruit, fruitCode, batchWeight, date);
    }

    public void askPrintDetails() {
        System.out.println("Would you like to print batch details?");
        System.out.println("\n1. YES");
        System.out.println("\n2. NO - FINISH");
        int correctBatch = Parser.decide(1,2);
        if (correctBatch == 1){
            printBatchDetails();
        } else if (correctBatch == 2){
            new Batch();
        }
    }

    private void printBatchDetails() {
        System.out.println("Batch Number: " + batchNumber + "\n"
                + "Recieved Date: " + date + "\n"
                + "Fruit Type" + finalFruit + "\n"
                + "Batch Weight: " + batchWeight + "kg" + "\n");
        System.out.println("\n");
        printWelcome();
    }
}
