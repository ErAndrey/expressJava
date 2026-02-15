package practice_5;

import practice_5.aquarium.*;
import practice_5.farm.*;
import practice_5.garden.*;
import practice_5.museum.*;
import practice_5.park.*;
import practice_5.pet.*;
import practice_5.restaraunt.*;
import practice_5.zoo.*;

public class Main {
    public static void main(String[] args) {
        //1. Зоопарк

        Zoo zoo1 = new Zoo();
        Zoo zoo2 = new Zoo();
        ZooManager zooManager = new ZooManager();
        Bird bird = new Bird();
        Elephant elephant = new Elephant();

        zooManager.addAnimalToZoo(zoo1, bird);
        zooManager.checkAnimal(zoo1);

        zooManager.addAnimalToZoo(zoo2, elephant);
        zooManager.checkAnimal(zoo2);

        //2. Управление домашними питомцами

        People people = new People();
        Cat cat = new Cat();
        Pet dog = new Dog();

        people.setPet(cat);
        people.playWithPet();
        people.feedPet();

        people.setPet(dog);
        people.playWithPet();
        people.feedPet();


        //3. Управление рестораном

        Menu menu = new Menu();
        Chief chief = new Chief();
        Drink water = new Drink("Освежающая", 50.0, 1);
        Hot soup = new Hot("Том ям", 350.5, 45);

        chief.checkDish(water);
        chief.checkDish(soup);

        chief.addDishToMenu(menu, water);
        chief.addDishToMenu(menu, soup);

        menu.readMenu();

        //4. Аквариум

        Aquarium aquarium = new Aquarium();
        Shark shark = new Shark();
        Star star = new Star();

        aquarium.demonstration();

        aquarium.setSeaCreature(shark);
        aquarium.demonstration();

        aquarium.setSeaCreature(star);
        aquarium.demonstration();

        //5. Ферма

        Farm farm = new Farm();
        Farmer farmer = new Farmer();

        Cow cow = new Cow();
        Chicken chicken = new Chicken();

        farmer.feed(farm);

        farmer.addAnimalToFarm(farm, cow);
        farmer.feed(farm);
        farmer.getResources(farm);

        farmer.addAnimalToFarm(farm, chicken);
        farmer.feed(farm);
        farmer.getResources(farm);

        //6. Ботанический сад

        Garden garden = new Garden();
        Botanic botanic = new Botanic();

        Orchid orchid = new Orchid();
        Cactus cactus = new Cactus();

        botanic.addPlant(orchid);
        botanic.setGarden(garden);
        botanic.carePlat();

        botanic.addPlant(orchid);
        botanic.carePlat();
        botanic.addPlant(cactus);
        botanic.carePlat();

        //7. Парк развлечений

        Park park = new Park();
        Controller controller = new Controller();
        Carousel carousel = new Carousel("Карусель");
        RollerCoaster rollerCoaster = new RollerCoaster("Американские горки");

        park.setAttraction(carousel);
        controller.tellAboutAttraction(park);
        park.setAttraction(rollerCoaster);
        controller.tellAboutAttraction(park);

        //8. Музей
        Museum m1 = new Museum();
        Museum m2 = new Museum();

        Manager manager = new Manager();

        Sculpture sculpture = new Sculpture("Я древняя скульптура", "нуждается в реставрации");
        Manuscript manuscript = new Manuscript("Я манускрипт", "требует контролируемой влажности");

        manager.tellAboutExhibitAtMuseum();
        manager.addExhibitToMuseum(m1, sculpture);
        manager.addExhibitToMuseum(m2, manuscript);

        manager.goWorkInMuseum(m1);
        manager.tellAboutExhibitAtMuseum();

        manager.goWorkInMuseum(m2);
        manager.tellAboutExhibitAtMuseum();

    }
}
