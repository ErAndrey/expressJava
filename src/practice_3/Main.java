package practice_3;

public class Main {
    public static void main(String[] args) {
        Company c = new Company(1, "S");
        Company s = new Company(1, "1");

        Company.companyName = "z";

        c.printCompanyName();
        s.printCompanyName();

        double r = 14.0;

        System.out.println(MathConstants.calculateCircleArea(r));
        System.out.println(MathConstants.calculateCircumference(r));
        Library library = new Library();

        University q = new University("A");
        University w = new University("B");
        University e = new University("C");

        University.changeUniversityName("Java");

        q.printStudentInfo();
        w.printStudentInfo();
        e.printStudentInfo();


        GameSettings.setMaxPlayers(5);

        GameSettings gameSettings1 = new GameSettings("Igra1", 12);
        GameSettings gameSettings2 = new GameSettings("Igra2", 4);

        gameSettings1.printGameStatus();
        gameSettings2.printGameStatus();

        gameSettings1.addPlayer();
        gameSettings2.addPlayer();
        gameSettings2.addPlayer();

        gameSettings1.printGameStatus();
        gameSettings2.printGameStatus();


        Person p1 = new Person("Andrew", "Eroshkin", "123");
        Person p2 = new Person("Sasha", "Eroshkin", "1234");
        Person p3 = new Person("Danil", "Eroshkin", "1235");


        p1.setFirstName("Andrey");

        p1.printPersonInfo();
        p2.printPersonInfo();
        p3.printPersonInfo();
    }
}
