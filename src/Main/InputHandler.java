import java.util.Scanner;

public class InputHandler {
    private static final Scanner reader = new Scanner(System.in); // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public InputHandler() {
    }

    public static int decide(int minChoice, int maxChoice) {
        int tempchoice = 0;
        int choice =0;
        boolean valid = false;
        if (reader.hasNextInt()) {
            tempchoice = reader.nextInt();
            if (tempchoice < minChoice) {
                System.out.println("No valid input - too low");
                decide(1,2);
            } else if (tempchoice > maxChoice) {
                System.out.println("No valid input - too high");
                decide(1,2);
            } else if (tempchoice >= minChoice && tempchoice <= maxChoice)
                choice = tempchoice;
                valid = true;
        }
        return choice;
    }
}




