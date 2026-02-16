package practice_3;

public class LibraryTest {
    public static void main(String[] args) {
        Library library = new Library();

        System.out.println(library.author);
        System.out.println(library.year);
        System.out.println(library.category);
        //System.out.println(library.bookTitle); - Нет доступа

        System.out.println(library.getAuthor());
        System.out.println(library.getYear());
        System.out.println(library.getCategory());
        System.out.println(library.getBookTitle()); // В отличии от public геттера
    }
}
