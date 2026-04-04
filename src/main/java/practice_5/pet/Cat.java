package practice_5.pet;

public class Cat extends Pet {
    @Override
    public void eat() {
        System.out.println("Ем влажный корм");
    }

    @Override
    public void play() {
        System.out.println("Я играю");
    }
}
