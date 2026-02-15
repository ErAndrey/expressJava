package practice_5.garden;

public class Botanic {
    private Garden garden;

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public void addPlant(Plant plant) {
        if (this.garden != null) {
            this.garden.setPlant(plant);
        } else {
            System.out.println("Ботаник не привязан к саду (Нет смены)");
        }
    }

    public void carePlat() {
        if (this.garden != null) {
            if (this.garden.getPlant() != null) {
                this.garden.getPlant().care();
            } else {
                System.out.println("В саду нет растения");
            }
        } else {
            System.out.println("Ботаник не привязан к саду (Нет смены)");
        }
    }
}
