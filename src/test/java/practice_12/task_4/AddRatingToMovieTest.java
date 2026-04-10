package practice_12.task_4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"synchronized void addRatingToMovie(Movie movie, Rating<T> rating)\"")
public class AddRatingToMovieTest extends Preconditions {

    /**
     * Positive :
     * [1, 10] -> Добавится
     * Добавили одну оценку к фильму x
     * Добавили вторую оценку к фильму x
     * Добавили третью оценку к фильму y
     *
     * Negative :
     * [x, 1) -> IllegalArgumentException
     * (10, y] -> IllegalArgumentException
     * Movie null -> IllegalArgumentException
     * Rating null -> IllegalArgumentException
     */

    @Test
    void addRatingToMovie_CanAddRatingToMovie() {
        assertTrue(targetClass.getMovies().isEmpty());

        targetClass.addRatingToMovie(m1, r1);

        assertEquals(1, targetClass.getMovies().size());
        assertEquals(1, targetClass.getMovies().get(m1).size());

        assertTrue(targetClass.getMovies().containsKey(m1));
        assertEquals(r1, targetClass.getMovies().get(m1).getFirst());

        Thread t1 = new Thread(() -> {
            targetClass.addRatingToMovie(m1, r3);
        });

        Thread t2 = new Thread(() -> {
            targetClass.addRatingToMovie(m1, r8);
        });

        Thread t3 = new Thread(() -> {
            targetClass.addRatingToMovie(m2, r1);
        });

        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertEquals(2, targetClass.getMovies().size());
        assertEquals(3, targetClass.getMovies().get(m1).size());
        assertEquals(1, targetClass.getMovies().get(m2).size());
    }

    @Test
    void addRatingToMovie_ReturnsException_WhenRatingLowerBy1() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.addRatingToMovie(m1, new Rating<>(0)));
    }

    @Test
    void addRatingToMovie_ReturnsException_WhenRatingMoreBy10() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.addRatingToMovie(m1, new Rating<>(11)));
    }

    @Test
    void addRatingToMovie_ReturnsException_WhenMovieIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.addRatingToMovie(null, r1));
    }

    @Test
    void addRatingToMovie_ReturnsException_WhenRatingIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.addRatingToMovie(m1, null));
    }
}
