import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//The Processor class processes print statements, retrieves input and if valid passes input to Batch file to be saved.
//The Processor class also saves batch details to a json file in a defined filepath.
public class Processor {

    //We create instances of Batch and Fruit to access methods to get and set data.
    Batch b = new Batch();
    Fruit fruit = new Fruit();

    //When called in Main, the program starts and the user can begin to interact with the system
    public Processor() {
        printWelcome();
    }

    //Message is displayed to the user to begin the
    private void printWelcome() {
        System.out.print("Welcome to Renfrewshire Soft Fruits Co batch system.");
        System.out.print("This system allows you to create batches of fruit.");
        System.out.println("Please select a function.");
        System.out.println("1. Create a new batch");
        System.out.println("2. List all Batches");
        System.out.println("3. View details of a batch");
        System.out.println("4. Sort/Grade a batch");
        System.out.println("5. Quit");
        System.out.println("Please enter 1 to 5");
        startChoice();
    }

    //The date is generated and set within Batch
    public void generateDate() {
        DateTimeFormatter dateformat;
        dateformat = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dateformat.format(now);
        b.setDate(date);
        displayDate();
    }

    //Based on the input, decide whether to create a new batch, list batches,
    // get specific batch details, sort/grade batches or quit the system.
    public void startChoice() {
        int startChoice = InputHandler.decide(1, 5);
        if (startChoice == 1) {
            generateDate();
        } else if (startChoice == 2) {
            listAllBatches();
        } else if (startChoice == 3) {
            viewBatchDetail();
        } else if (startChoice == 4) {
            sortGradeBatches();
        } else if (startChoice == 5) {
            System.out.println("Thank you for using Renfewshire Soft Fruits batch system");
            System.exit(0);
        }
    }

    //Display the date previously set
    public void displayDate() {
        System.out.println("Date: " + b.getDate());
        askFarmCode();
    }

    //Ask user to enter farm code and set if valid
    public void askFarmCode() {
        System.out.print("Enter Farm Number (000 to 999)");
        System.out.print("\n >");
        int minFarmCode = 0;
        int maxFarmCode = 999;
        int tempFarm = InputHandler.decide(minFarmCode, maxFarmCode);
        b.setFarmCode(String.format("%03d", tempFarm));
        printFarmCode();
    }

    //print the farmCode after set
    private void printFarmCode() {
        System.out.println("Farm Code: " + b.getFarmCode());
        fruit.askFruitType();
        b.setFruit(fruit.returnFruit());
        b.setFruitCode(fruit.returnFruitCode());
        System.out.println("You choose: " + b.getFruit());
        askBatchWeight();
    }

    //Ask user to enter batch weight
    private void askBatchWeight() {
        System.out.println("Enter Batch Weight in KGs(max weight per batch is 100kg)" + "\n >");
        b.setBatchWeight(InputHandler.decide(1, 100));
        System.out.println("Batch Weight :" + b.getBatchWeight() + "kgs");
        validateBatch();
    }

    //Confirm whether batch is correct before saving, or go back to start of new batch
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

    //generate the individual batch number that is used for saving the file
    private void generateBatchNumber() {
        String temp = b.getDate() + "-" + b.getFruitCode() + "-" + b.getFarmCode();
        b.setBatchNumber(temp);
        saveJsonfile();
        askPrintDetails();
    }

    //Decide whether to print details or finish creating this batch and start again
    public void askPrintDetails() {
        System.out.println("Would you like to print batch details?");
        System.out.println("\n1. YES");
        System.out.println("\n2. NO - FINISH");
        int correctBatch = InputHandler.decide(1, 2);
        if (correctBatch == 1) {
            printBatchDetails();
        } else if (correctBatch == 2) {
            new Processor();
        }
    }

    //once batch is printed it then goes back to the start
    private void printBatchDetails() {
        System.out.println("Batch Number: " + b.getBatchNumber() + "\n"
                + "Recieved Date: " + b.getDate() + "\n"
                + "Fruit Type" + b.getFruit() + "\n"
                + "Batch Weight: " + b.getBatchWeight() + "kg" + "\n");
        new Processor();
    }

    //save the batch details to a json file in a specified filepath
    public void saveJsonfile() {
        JSONObject batchDetails = new JSONObject();
        batchDetails.put("Batch Number:", b.getBatchNumber());
        batchDetails.put("Recieved Date: ", b.getDate());
        batchDetails.put("Fruit: ", b.getFruit());
        batchDetails.put("Batch Weight: ", b.getBatchWeight() + "kgs");

        JSONObject batchObject = new JSONObject();
        batchObject.put("Batch Details: ", batchDetails);

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
        } else {
            System.out.println("This batch" + b.getBatchNumber() + " already exists");
        }
    }

    //JSON parser object to parse read file
    //first retrieve the names of all files,
    // and using a loop get each file, list batch details then get next file
    private void listAllBatches() {
        //sources to credit: https://howtodoinjava.com/library/json-simple-read-write-json-examples/#maven
        //https://stackoverflow.com/questions/36240647/reading-multiple-files-in-a-folder-using-java

        File[] listOfBatches = null;
        String[] myDocs;

        File batchOutputFolder = new File("C:\\Users\\GA\\Documents\\Year1\\CS112 Programming 1- T3 Project\\step1Output\\");
        listOfBatches = batchOutputFolder.listFiles();
        myDocs = new String[listOfBatches.length];
        for (int i = 0; i < listOfBatches.length; i++) {
            myDocs[i] = listOfBatches[i].getName();
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader("C:\\Users\\GA\\Documents\\Year1\\CS112 Programming 1- T3 Project\\step1Output\\" + myDocs[i])) {
                //Read JSON file
                Object obj = jsonParser.parse(reader);
                JSONArray batchList = new JSONArray();
                batchList.add(obj);
                System.out.println("File: " + listOfBatches[i].getName());
                System.out.println(batchList);
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
        printWelcome();
    }

    private void sortGradeBatches() {

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("C:\\Users\\GA\\Documents\\Year1\\CS112 Programming 1- T3 Project\\step1Output\\")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray batchList = new JSONArray();
            batchList.add(obj);
            System.out.println(batchList);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        
    }

    private void viewBatchDetail() {
        System.out.print("Please enter in the batch you would like to view. E.g. 10062020-ST-000\n");
        String file = InputHandler.getFile();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("C:\\Users\\GA\\Documents\\Year1\\CS112 Programming 1- T3 Project\\step1Output\\" + file + ".json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray batchList = new JSONArray();
            batchList.add(obj);
            System.out.println(batchList);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        printWelcome();
    }


}

