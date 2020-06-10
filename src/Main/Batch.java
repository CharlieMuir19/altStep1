//The Batch class sets and gets all important information in each batch
public class Batch {
    private String date;
    private String farmCode;
    private String finalFruit;
    private String fruitCode;
    private int batchWeight;
    private String batchNumber;

    //begins gathering information from user
    public Batch() {
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFarmCode(String farmCode) {
        this.farmCode = farmCode;
    }

    public void setBatchWeight(int decide) {
        batchWeight = decide;
    }

    public String getFarmCode() {
        return farmCode;
    }

    public String getFruit() {
        return finalFruit;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public String getFruitCode() {
        return fruitCode;
    }

    public String getDate() {
        return date;
    }

    public int getBatchWeight() {
        return batchWeight;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public void setFruit(String returnFruit) {
        finalFruit = returnFruit;
    }

    public void setFruitCode(String returnFruitCode) {
        fruitCode = returnFruitCode;
    }
}
