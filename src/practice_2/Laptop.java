package practice_2;

public class Laptop {
    String brand;
    int price;

    public Laptop (String brand, int price) {
        this.brand = brand;
        this.price = price;
    }

    public String getBrand () {return this.brand;}
    public int getPrice () {return this.price;}

    public void setPrice (int newPrice) {
        this.price = newPrice;
    }

    public void setBrand (String newBrand) {
        this.brand = newBrand;
    }

    public void printInfo () {
        System.out.println(this.brand + ", " + this.price);
    }
}
