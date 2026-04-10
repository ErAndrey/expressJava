package practice_12.task_4;

import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        MovieService<Double> movieService = new MovieService<>();

        try {
            System.out.println(movieService.getAveragingRatingForMovies());
        } catch (NoSuchElementException e) {
            System.out.println("Отловили: " + e.getMessage());
        }

        Movie m1 = new Movie("Гарри Поттер");
        Movie m2 = new Movie("Властелин колец");
        Movie m3 = new Movie("Выживший");

        Rating<Double> r1 = new Rating<>(4.4);
        Rating<Double> r2 = new Rating<>(10.0);
        Rating<Double> r3 = new Rating<>(5.5);
        Rating<Double> r4 = new Rating<>(7.8);
        Rating<Double> r5 = new Rating<>(9.0);

        movieService.addRatingToMovie(m1, r3);
        movieService.addRatingToMovie(m1, r4);
        movieService.addRatingToMovie(m1, r5);
        movieService.addRatingToMovie(m2, r1);
        movieService.addRatingToMovie(m2, r2);

        System.out.println(movieService.getAveragingRatingForMovies());
    }
}
