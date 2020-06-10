import jdk.internal.util.xml.impl.Input;

public class Fruit {

    private String finalFruit;
    private String fruitCode;

    void askFruitType() {
        System.out.println("Choose a fruit: " +
                "\n1. Strawberry " +
                "\n2. Raspberry " +
                "\n3. Blackberry " +
                "\n4. Gooseberry " +
                "\n >");

        int choice = InputHandler.decide(1,4);
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

    }

    public void setSTFruit() {
        finalFruit = "Strawberry";
        fruitCode = "ST";
    }
    public void setRAFruit() {
        finalFruit = "Raspberry";
        fruitCode = "RA";
    }
    public void setBLFruit() {
        finalFruit = "Blackberry";
        fruitCode = "BL";
    }
    public void setGOFruit(){
        finalFruit = "Gooseberry";
        fruitCode = "GO";
    }

    public String returnFruit(){
        return finalFruit;
    }

    public String returnFruitCode(){
        return fruitCode;
    }
}
