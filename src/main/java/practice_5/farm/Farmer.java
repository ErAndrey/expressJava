package practice_5.farm;

public class Farmer {
    public void addAnimalToFarm(Farm farm, Animal animal) {
        farm.setAnimal(animal);
    }

    public void feed(Farm farm) {
        if (isAnimalPresentOnFarm(farm)) {
            farm.getAnimal().eat();
        }
    }

    public void getResources(Farm farm) {
        if (isAnimalPresentOnFarm(farm)) {
            farm.getAnimal().getResource();
        }

    }

    public boolean isAnimalPresentOnFarm(Farm farm) {
        if (farm.getAnimal() == null) {
            System.out.println("На ферме нет животного");
            return false;
        }
        return true;
    }
}
