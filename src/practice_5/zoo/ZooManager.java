package practice_5.zoo;

public class ZooManager {
    public void addAnimalToZoo(Zoo zoo, Animal animal) {
        zoo.setAnimal(animal);
    }

    public void checkAnimal(Zoo zoo) {
        if (zoo.getAnimal() != null) {
            zoo.getAnimal().move();
            zoo.getAnimal().voice();
        }
    }
}
