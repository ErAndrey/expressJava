package additional.n5_oop_principles.task_18;

public class Main {
    public static void main(String[] args) {

        Library library = new Library();

        Book a = new Book("Java", "Danila", 2025);
        Book b = new Book("Iris", "Andrew", 2022);
        Book c = new Book("Dota", "Anton", 2019);
        Book d = new Book("Java", "Bogat", 2015);
        Book e = new Book("Java", "Sasha", 2000);
        Book f = new Book("X", "Mila", 2005);
        Book g = new Book("Z", "Kolya", 2005);

        library.addBook(a);
        library.addBook(b);
        library.addBook(c);
        library.addBook(d);
        library.addBook(e);
        library.addBook(f);
        library.addBook(g);

        library.seeAllBooks();
        library.searchABook();

    }
}
