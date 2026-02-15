package practice_5.park;

public class RollerCoaster extends Attraction {

    public RollerCoaster(String description) {
        super(description);
    }

    @Override
    public void care() {
        System.out.println(this.getDescription() + ": Проверка безопасности");
    }
}
