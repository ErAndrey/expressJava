package practice_5.farm;

public class Chicken extends Animal {
    @Override
    public void get() {
        System.out.println("Я снесу вам яйца");
    }

    @Override
    public void eat() {
        System.out.println("Я кушаю зернышки");
    }
}
