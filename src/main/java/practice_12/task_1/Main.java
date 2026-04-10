package practice_12.task_1;

public class Main {
    public static void main(String[] args) {
        EntityManager<Developer> entityManager = new EntityManager<>();

        Developer d0 = new Developer("Lera", 35, false);
        Developer d1 = new Developer("Andrew", 12, false);
        Developer d2 = new Developer("Sasha", 18, true);
        Developer d3 = new Developer("Danila", 24, false);
        Developer d4 = new Developer("Mila", 19, true);
        Developer d5 = new Developer("Vasya", 37, false);
        Developer d6 = new Developer("Kolya", 40, true);
        Developer d7 = new Developer("Andrew", 56, false);
        Developer d8 = new Developer("Nikita", 89, false);
        Developer d9 = new Developer("Ilya", 24, true);

        System.out.println("Нет Entity: " + entityManager.getEntities()); // Нет entity

        entityManager.addEntity(d0);
        entityManager.addEntity(d1);
        entityManager.addEntity(d1); // Дубль, не должен добавиться
        entityManager.addEntity(d2);
        entityManager.addEntity(d3);
        entityManager.addEntity(d4);
        entityManager.addEntity(d5);
        entityManager.addEntity(d6);
        entityManager.addEntity(d7);
        entityManager.addEntity(d8);
        entityManager.addEntity(d9);
        entityManager.addEntity(d9); // Дубль, не должен добавиться

        System.out.println("Есть все Entity (" +  entityManager.getEntities().size() + "): " + entityManager.getEntities()); // 10

        System.out.println(entityManager.removeEntity(d0)); // true
        System.out.println(entityManager.removeEntity(d0)); // false

        System.out.println("Есть все Entity (" +  entityManager.getEntities().size() + "): " + entityManager.getEntities()); // 9

        System.out.println("Фильтрация по имени: Andrew " + entityManager.filterByName("Andrew"));
        System.out.println("Фильтрация по возрасту [18, 30]: " + entityManager.filterByAge(18, 30));
        System.out.println("Фильтрация по активности: " + entityManager.filterByActive(true));
    }
}
