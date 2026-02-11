package practice_2;

public class Main {
    public static void main(String[] args) {

        Car car = new Car("BMW", 2026);
        car.setBrand("Y");
        car.print();

        Rectangle rectangle = new Rectangle(54, 32);
        rectangle.setWidth(32);
        System.out.println(rectangle.calculateArea());

        Book a = new Book("Гарри Поттер", "Джоан Роулинг");
        a.setAuthor("Андрей");
        a.printInfo();

        BankAccount account = new BankAccount("Андрей");
        account.deposit(11);
        account.withdraw(20);
        account.withdraw(9);
        account.printBalance();

        Point b = new Point(3, 5);
        b.setX(5);
        b.print();

        StudentGroup studentGroup = new StudentGroup("Прогеры", -2);
        System.out.println(studentGroup.getStudentCount());
        studentGroup.setStudentCount(20);
        studentGroup.printInfo();

        Circle c = new Circle(20);
        c.setRadius(11);
        System.out.println(c.calculateCircumference());
        System.out.println(c.calculateArea());

        Teacher teacher = new Teacher("Андрей", "Java");
        teacher.setSubject("Express Java");
        System.out.println(teacher.getInfo());

        Product cocaCola = new Product("Кока-Кола", 20.0);

        System.out.println(cocaCola.getPrice());
        cocaCola.setPrice(105.0);
        cocaCola.applyDiscount(20);
        System.out.println(cocaCola.getPrice());

        Laptop l = new Laptop("Zzz", 113);
        l.setPrice(666);
        l.printInfo();

    }
}
