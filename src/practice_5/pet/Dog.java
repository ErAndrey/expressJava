package practice_5.pet;

public class Dog extends Pet {
    @Override
    public void eat() {
        System.out.println("Ем сухой корм");
    }

    @Override
    public void play() {
        System.out.println("Гуляю");
    }
}
