package practice_5.pet;

public class People {
    private Pet pet;

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void playWithPet() {
        if (iHaveAPet()) {
            this.pet.play();
        }
    }

    public void feedPet() {
        if (iHaveAPet()) {
            this.pet.eat();
        }
    }

    private boolean iHaveAPet() {
        return this.pet != null;
    }
}
