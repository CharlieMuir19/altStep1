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

    }

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

    public String returnFruit(){
        return finalFruit;
    }

    public String returnFruitCode(){
        return fruitCode;
    }
}
