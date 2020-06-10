import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Processor {

    Batch b = new Batch();
    Fruit fruit = new Fruit();

    public Processor() {
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

    //The date is generated and stored to constructor
    public void generateDate() {
        DateTimeFormatter dateformat;
        dateformat = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dateformat.format(now);
        b.setDate(date);
        displayDate();
    }

    public void startChoice() {
        int createBatch = 1;
        int quit = 2;
        int startChoice = InputHandler.decide(createBatch, quit);
        if (startChoice == 2) {
            System.exit(0);
        }
    }

    public void displayDate() {
        System.out.println("Date: " + b.getDate());
        askFarmCode();
    }

    public void askFarmCode() {
        System.out.print("Enter Farm Number (000 to 999)");
        System.out.print("\n >");
        int minFarmCode = 0;
        int maxFarmCode = 999;
        int tempFarm = InputHandler.decide(minFarmCode, maxFarmCode);
        b.setFarmCode(String.format("%03d", tempFarm));
        printFarmCode();
    }

    private void printFarmCode() {
        System.out.println("Farm Code: " + b.getFarmCode());
        fruit.askFruitType();
        b.setFruit(fruit.returnFruit());
        b.setFruitCode(fruit.returnFruitCode());
        System.out.println("You choose: " + b.getFruit());
        askBatchWeight();
    }

    private void askBatchWeight() {
        System.out.println("Enter Batch Weight in KGs(max weight per batch is 100kg)" + "\n >");
        b.setBatchWeight(InputHandler.decide(1, 100));
        System.out.println("Batch Weight :" + b.getBatchWeight() + "kgs");
        validateBatch();
    }

    private void validateBatch() {
        System.out.println("This batch contains " + b.getBatchWeight() + " kgs of " + b.getFruit() +
                " \nfrom Farm Number " + b.getFarmCode() + " recieved on " + b.getDate() + ". Is this correct?");
        System.out.println("1. YES - SAVE");
        System.out.println("2. NO - RESTART");
        int correctBatch = InputHandler.decide(1, 2);
        if (correctBatch == 1) {
            generateBatchNumber();
        } else if (correctBatch == 2) {
            generateDate();
        }
    }

    private void generateBatchNumber() {
        String temp = b.getDate() + "-" + b.getFruitCode() + "-" + b.getFarmCode();
        b.setBatchNumber(temp);
        saveJsonfile();
    }

    public void askPrintDetails() {
        System.out.println("Would you like to print batch details?");
        System.out.println("\n1. YES");
        System.out.println("\n2. NO - FINISH");
        int correctBatch = InputHandler.decide(1, 2);
        if (correctBatch == 1) {
            printBatchDetails();
        } else if (correctBatch == 2) {
            new Batch();
        }
    }

    private void printBatchDetails() {
        System.out.println("Batch Number: " + b.getBatchNumber() + "\n"
                + "Recieved Date: " + b.getDate() + "\n"
                + "Fruit Type" + b.getFruit() + "\n"
                + "Batch Weight: " + b.getBatchWeight() + "kg" + "\n");
        System.out.println("\n");
        new Processor();
    }

    public void saveDataFile(String batchNumber, String finalFruit, int batchWeight, String date) {
        try {
            Path filepath = Paths.get("C:\\Users\\GA\\Documents\\Year1\\CS112 Programming 1- T3 Project\\step1Output\\" + batchNumber + ".txt");
            java.io.File outputFile = filepath.toFile();
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter writer = new FileWriter(filepath.toString());
            writer.write("Batch Number: " + batchNumber + "\n"
                    + "Recieved Date: " + date + "\n"
                    + "Fruit Type: " + finalFruit + "\n"
                    + "Batch Weight: " + batchWeight + "kg" + "\n");
            writer.close();
        } catch (IOException ioe) {
            System.out.println("There was a problem writing to " + batchNumber + ".txt");
            System.out.println("Error message: " + ioe.getMessage());
        }
    }

    public void saveJsonfile() {
        JSONObject batchDetails = new JSONObject();
        batchDetails.put("Batch Number:", b.getBatchNumber());
        batchDetails.put("Recieved Date: ", b.getDate());
        batchDetails.put("Fruit: ", b.getFruit());
        batchDetails.put("Batch Weight: ", b.getBatchWeight() + "kgs");

        JSONObject batchObject = new JSONObject();
        batchObject.put("Batch Details: ", batchDetails);

        batchObject.get(b.getBatchNumber());
        //Add batch to list
        JSONArray batch = new JSONArray();
        //Write JSON file
        Path filepath = Paths.get("C:\\Users\\GA\\Documents\\Year1\\CS112 Programming 1- T3 Project\\step1Output\\" + b.getBatchNumber() + ".json");
        java.io.File outputFile = filepath.toFile();
        if (!outputFile.exists()) {
            try (FileWriter file = new FileWriter(String.valueOf(filepath))) {
                file.write(batchObject.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("This batch" + b.getBatchNumber() + " already exists");
        }
    }
}

