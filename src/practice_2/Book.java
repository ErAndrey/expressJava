package practice_2;

public class Book {
    String title;
    String author;

    public Book (String title, String author) {
        this.title = title;
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() { return this.title; }
    public String getAuthor() { return this.author; }

    public void printInfo() {
        System.out.println("Название: " + title + ", Автор: " + author);
    }
}
