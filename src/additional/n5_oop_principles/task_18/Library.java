package additional.n5_oop_principles.task_18;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Library {

    private final List<Book> books;
    private final Scanner scanner;

    public Library() {
        this.books = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void seeAllBooks() {
        int countBook = this.books.size();
        System.out.println("Список книг: " + countBook + " штук");
        int i = 1;
        for (Book book : this.books) {
            System.out.println(i + ". " + book);
            i++;
        }
    }

    private String listBooksIsEmpty(List<Book> books) {
        if (books.isEmpty()) {
            return "Увы, книг не найдено";
        } else {
            return "Найденные книги:";
        }
    }

    private List<Book> filterBooks(List<Book> books, String type) {
        List<Book> searchedBooks = new ArrayList<>();
        System.out.print("Введите значение для " + type + " : ");
        String value = scanner.next();
        for (Book book : books) {
            boolean match = switch (type) {
                case "И" -> book.getName().equalsIgnoreCase(value);
                case "А" -> book.getAuthor().equalsIgnoreCase(value);
                case "Г" -> String.valueOf(book.getYear()).equals(value);
                default -> false;
            };
            if (match) {
                searchedBooks.add(book);
            }
        }
        return searchedBooks;
    }

    public void searchABook() {
        System.out.println(
                "Книга содержит: Имя, Автора, Год выпуска\n" +
                        "Введите критерий, по которому хотите найти книгу: 'И' / 'А' / 'Г', " +
                        "или, комбинацию критериев в виде 'АИГ' / 'ГИ'"
        );

        System.out.print("Критерий поиска: ");
        String mainType = scanner.next();
        String res;

        boolean goNext = true;
        List<Book> searchedBooks = new ArrayList<>();

        switch (mainType) {
            case "А" -> {
                searchedBooks = filterBooks(this.books, "А");
                res = listBooksIsEmpty(searchedBooks);
            }
            case "И" -> {
                searchedBooks = filterBooks(this.books, "И");
                res = listBooksIsEmpty(searchedBooks);
            }
            case "Г" -> {
                searchedBooks = filterBooks(this.books, "Г");
                res = listBooksIsEmpty(searchedBooks);
            }
            case "АИ", "ИА" -> {
                List<Book> a = filterBooks(this.books, "А");
                searchedBooks = filterBooks(a, "И");
                res = listBooksIsEmpty(searchedBooks);
            }
            case "АГ", "ГА" -> {
                List<Book> a = filterBooks(this.books, "А");
                searchedBooks = filterBooks(a, "Г");
                res = listBooksIsEmpty(searchedBooks);
            }
            case "ГИ", "ИГ" -> {
                List<Book> a = filterBooks(this.books, "И");
                searchedBooks = filterBooks(a, "Г");
                res = listBooksIsEmpty(searchedBooks);
            }
            case "АИГ", "АГИ", "ИАГ", "ИГА", "ГАИ", "ГИА" -> {
                List<Book> a = filterBooks(this.books, "А");
                List<Book> b = filterBooks(a, "И");
                searchedBooks = filterBooks(b, "Г");
                res = listBooksIsEmpty(searchedBooks);
            }
            default -> {
                goNext = false;
                System.out.println("Нет таких критериев");
                res = listBooksIsEmpty(searchedBooks);
            }
        }

        if (goNext) {
            System.out.println(res);
            int i = 1;
            for (Book book : searchedBooks) {
                System.out.println(i + ". " + book);
                i++;
            }
        } else {
            System.out.print("Попробуйте еще раз");
        }
    }

}
