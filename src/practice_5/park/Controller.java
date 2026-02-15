package practice_5.park;

public class Controller {
    public void tellAboutAttraction(Park park) {
        if (park.getAttraction() != null) {
            park.getAttraction().care();
        }
    }
}
