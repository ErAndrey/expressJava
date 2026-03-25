package test.task_3;

public class Person  {
    private String name;
    private int age;
    private String city;

    public Person(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getName() { return this.name; }
    public int getAge() { return this.age; }
    public String getCity() { return this.city; }
    @Override
    public String toString() {
        return "{" + this.name + ", " + this.age + ", " + this.city + "}";
    }
}
