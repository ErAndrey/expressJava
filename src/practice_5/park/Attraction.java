package practice_5.park;

public abstract class Attraction {
    private String description;

    public Attraction(String description) {
        this.description = description;
    }

    protected abstract void care();

    public String getDescription() {
        return this.description;
    }
}
