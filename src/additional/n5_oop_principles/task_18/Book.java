package additional.n5_oop_principles.task_18;

public final class Book {

    private final String name;
    private final String author;
    private final Integer year;


    public Book(String name, String author, Integer year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public Integer getYear() {
        return this.year;
    }

    @Override
    public String toString() {
        return "\"" + this.name + ", " + this.author + ", " + this.year + "\"";
    }

}
