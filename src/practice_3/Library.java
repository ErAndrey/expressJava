package practice_3;

public class Library {
    private String bookTitle;
    protected String author;
    int year;
    public String category;

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
