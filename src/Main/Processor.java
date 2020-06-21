
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//The Processor class processes print statements, retrieves input and if valid passes input to Batch file to be saved.
//The Processor class also saves batch details to a json file in a defined filepath.
public class Processor {

    //We create instances of Batch, Fruit, JSONProcessor and InputHandler to access methods to get and set data.
    private Batch b;
    private Fruit fruit;
    private JSONProcessor jsonP;
    private InputHandler inputHandler;

    //Processor constructor sets batch, fruit and inputhandler to start, and creates a new JSONProcessor
    public Processor(Batch b, Fruit fruit, InputHandler inputHandler) {
        this.b = b;
        this.fruit = fruit;
        this.inputHandler = inputHandler;
        this.jsonP = new JSONProcessor(inputHandler);
    }

    //Main calls this method to begin the program, displays welcome to the user
    public void addNewBatch() {
        System.out.print("Welcome to Renfrewshire Soft Fruits Co batch system.");
        System.out.print("This system allows you to create batches of fruit.");
        selectFunction();
    }

    //displays to the user the options they can take inside the program
    private void selectFunction() {
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
    private void generateDate() {
        DateTimeFormatter dateformat;
        dateformat = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dateformat.format(now);
        b.setDate(date);
        displayDate();
    }

    //Based on the input, decide whether to create a new batch, list batches,
    // get specific batch details, sort/grade batches or quit the system.
    private void startChoice() {
        int startChoice = inputHandler.decide(1, 5);
        if (startChoice == -1) {
            startChoice();
        } else if (startChoice == 1) {
            generateDate();
        } else if (startChoice == 2) {
            jsonP.listAllBatches();
            selectFunction();
        } else if (startChoice == 3) {
            viewBatchDetail();
        } else if (startChoice == 4) {
            sortGradeBatches();
        } else if (startChoice == 5) {
            System.out.println("Thank you for using Renfewshire Soft Fruits batch system");
            System.exit(0);
        }
    }

    //If the user selects to grade a batch, the program displays the data and recieves the file from user,
    //the JSONProcesor method to get the grades from the user
    private void sortGradeBatches() {
        System.out.print("Please enter in the batch you would like to grade. E.g. 11062020-ST-000\n");
        String file = inputHandler.getFile();
        boolean graded = jsonP.getGradeBatches(file);
        //if the details are successfully graded and saved to file, we then ask the user to select next function
        if (graded) {
            this.selectFunction();
            //if details dont save, then we iterate the method again until graded
        } else {
            sortGradeBatches();
        }

    }

    //Display the date
    private void displayDate() {
        System.out.println("Date: " + b.getDate());
        askFarmCode();
    }

    //Ask user to enter farm code and set if valid
    private void askFarmCode() {
        System.out.print("Enter Farm Number (000 to 999)");
        System.out.print("\n >");
        int minFarmCode = 0;
        int maxFarmCode = 999;
        int tempFarm = inputHandler.decide(minFarmCode, maxFarmCode);
        if (tempFarm == -1) {
            askFarmCode();
        } else {
            b.setFarmCode(String.format("%03d", tempFarm));
            printFarmCode();
        }
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
        int batchWeight = InputHandler.decide(1, 100);
        if (batchWeight == -1) {
            askBatchWeight();
        } else {
            b.setBatchWeight(batchWeight);
            System.out.println("Batch Weight :" + b.getBatchWeight() + "kgs");
            validateBatch();
        }
    }

    //Confirm whether batch is correct before saving, or go back to start of new batch
    private void validateBatch() {
        System.out.println("This batch contains " + b.getBatchWeight() + " kgs of " + b.getFruit() +
                " \nfrom Farm Number " + b.getFarmCode() + " recieved on " + b.getDate() + ". Is this correct?");
        System.out.println("1. YES - SAVE");
        System.out.println("2. NO - RESTART");
        int correctBatch = inputHandler.decide(1, 2);
        if (correctBatch == 1) {
            generateBatchNumber();
        } else if (correctBatch == 2) {
            generateDate();
        } else if (correctBatch == -1) {
            validateBatch();
        }
    }

    //generate the individual batch number that is used for saving the file
    private void generateBatchNumber() {
        String temp = b.getDate() + "-" + b.getFruitCode() + "-" + b.getFarmCode();
        b.setBatchNumber(temp);
        String fruitCode = b.getFruitCode();
        String date = b.getDate();
        String batchNumber = b.getBatchNumber();
        int batchWeight = b.getBatchWeight();
        jsonP.saveJsonfile(batchNumber, date, fruitCode, batchWeight);
        askPrintDetails();
    }

    //Decide whether to print details or finish creating this batch and start again
    private void askPrintDetails() {
        System.out.println("Would you like to print batch details?");
        System.out.println("\n1. YES");
        System.out.println("\n2. NO - FINISH");
        int correctBatch = inputHandler.decide(1, 2);
        if (correctBatch == 1) {
            printBatchDetails();
        } else if (correctBatch == 2) {
            this.selectFunction();
        } else if (correctBatch == -1) {
            askPrintDetails();
        }
    }

    //once batch is printed it then goes back to the start
    private void printBatchDetails() {
        System.out.println("Batch Number: " + b.getBatchNumber() + "\n"
                + "Recieved Date: " + b.getDate() + "\n"
                + "Fruit Type: " + b.getFruit() + "\n"
                + "Batch Weight: " + b.getBatchWeight() + "kg" + "\n");
        this.selectFunction();
    }

    //When user wants to view individual batch, recieves batch from user and displays back to user details within batch and grading
    private void viewBatchDetail() {
        System.out.print("Please enter in the batch you would like to view. E.g. 11062020-ST-000\n");
        String file = inputHandler.getFile();
        boolean saved = jsonP.viewSingleBatch(file);
        //if the details are successfully viewed, then we ask user to select next function
        if (saved) {
            this.selectFunction();
            //if not viewed, we ask user to enter new batch number to view individually
        } else {
            viewBatchDetail();
        }
    }

}