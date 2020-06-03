import java.util.Scanner;

public class Parser {
    private static final Scanner reader = new Scanner(System.in); // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() {
    }

    public static int decide(int minChoice, int maxChoice) {
        int choice = 0;

        if (reader.hasNextInt()) {
            choice = reader.nextInt();
            //String tempChoice = Integer.toString(choice);
            //while(!reader.hasNextInt()){
            if (choice < minChoice) {
                System.out.println("No valid input - too low");
                reader.nextInt();
            } else if (choice > maxChoice) {
                System.out.println("No valid input - too high");
                reader.nextInt();
//            } else if (tempChoice.matches(".*[a-z].*"))
//                System.out.println("Error - cannot enter characters, enter a valid number ");
//            reader.nextInt();
            }
        }return choice;
    }
}



