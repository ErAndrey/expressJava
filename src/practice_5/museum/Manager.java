package practice_5.museum;

public class Manager {
    private Museum museum;

    public void goWorkInMuseum(Museum museum) {
        this.museum = museum;
    }

    public void addExhibitToMuseum(Museum museum, Exhibit exhibit) {
        museum.setExhibit(exhibit);
    }

    public void tellAboutExhibitAtMuseum() {
        if (this.museum != null) {
            if (this.museum.getExhibit() != null) {
                this.museum.getExhibit().getInfo();
            }
        }
    }
}
