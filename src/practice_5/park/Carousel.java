package practice_5.park;

public class Carousel extends Attraction {

    public Carousel(String description) {
        super(description);
    }

    @Override
    public void care() {
        System.out.println(this.getDescription() + ": Техническое обслуживание");
    }
}
