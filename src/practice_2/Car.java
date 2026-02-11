package practice_2;

public class Car {
    String brand;
    int year;

    public Car (String brand, int year) {
        this.brand = brand;
        this.year = year;
    }

    public String getBrand() {
        return this.brand;
    }

    public int getYear() {
        return this.year;
    }

    public void setBrand(String newBrand) {
        this.brand = newBrand;
    }

    public void setYear(int newYear) {
        this.year = newYear;
    }

    public void print() {
        System.out.println("Марка: " + this.brand + "\nГод выпуска: " + this.year);
    }

}
