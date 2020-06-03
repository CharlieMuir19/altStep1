import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Batch {

    private String date;
    private int farmCode;
    private String finalFruit;
    private String fruitCode;

    //begins gathering information from user
    public Batch() {
        printWelcome();
        startChoice();
        generateDate();
        askFarmCode();
        askFruitType();

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
    }

    public void askFarmCode() {
        System.out.print("\nEnter Farm Number (000 to 999)");
        System.out.print("\n >");
        int minFarmCode = 0;
        int maxFarmCode = 999;
        farmCode = Parser.decide(minFarmCode, maxFarmCode);
        printFarmCode();
    }

    private void printFarmCode() {
        System.out.println("Farm Code: " + farmCode);
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
        int choice = Parser.decide(1, 100);


    }


    private void setSTFruit() {
        finalFruit = "Strawberry";
        fruitCode = "ST";
    }
    private void setRAFruit() {
        finalFruit = "Raspberry";
        fruitCode = "RA";
    }
    private void setBLFruit() {
        finalFruit = "Blackberry";
        fruitCode = "BL";
    }
    private void setGOFruit(){
        finalFruit = "Gooseberry";
        fruitCode = "GO";
    }
}
