package practice_5.aquarium;

public class Aquarium {
    SeaCreature seaCreature;

    public void setSeaCreature(SeaCreature seaCreature) {
        this.seaCreature = seaCreature;
    }

    public void demonstration() {
        if (this.seaCreature != null) {
            this.seaCreature.move();
        } else {
            System.out.println("Аквариум пуст");
        }
    }
}
