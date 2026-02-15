package practice_5.museum;

public abstract class Exhibit {
    private String history;
    private String storageConditions;

    public Exhibit(String history, String storageConditions) {
        this.history = history;
        this.storageConditions = storageConditions;
    }

    public String getHistory() {
        return this.history;
    }

    public void getInfo() {
        System.out.println(this.history + ", " + this.storageConditions);
    }
}
