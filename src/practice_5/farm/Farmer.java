package practice_5.farm;

public class Farmer {
    public void addAnimalToFarm(Farm farm, Animal animal) {
        farm.setAnimal(animal);
    }

    public void feed(Farm farm) {
        if (farm.animal != null) {
            farm.animal.eat();
        } else {
            System.out.println("На ферме нет животного");
        }
    }

    public void getResources(Farm farm) {
        if (farm.animal != null) {
            farm.animal.get();
        } else {
            System.out.println("На ферме нет животного");
        }
    }
}
