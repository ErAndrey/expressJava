package practice_12.task_4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for : \"Map<Movie, Double> getAveragingRatingForMovies()\"")
public class GetAveragingRatingForMoviesTest extends Preconditions {
    /**
     * Positive :
     * m1 : [r1] -> 1.0
     * m2 : [r1, r3] -> 2.0
     *
     * m1 : [r1, r3, r10] -> 14/3
     * m2 : [r1, r3, r5, r8] -> 17/4
     *
     * Corner :
     * m1 : [] -> NoSuchElementException
     */

    @Test
    void getAveragingRatingForMovies_ReturnsCorrectRatingForMovies() {
        targetClass.addRatingToMovie(m1, r1);
        targetClass.addRatingToMovie(m2, r1);
        targetClass.addRatingToMovie(m2, r3);

        assertEquals(1.0, targetClass.getAveragingRatingForMovies().get(m1));
        assertEquals(2.0, targetClass.getAveragingRatingForMovies().get(m2));

        targetClass.addRatingToMovie(m1, r3);
        targetClass.addRatingToMovie(m1, r10);
        targetClass.addRatingToMovie(m2, r5);
        targetClass.addRatingToMovie(m2, r8);

        assertEquals(4.67, targetClass.getAveragingRatingForMovies().get(m1));
        assertEquals(4.25, targetClass.getAveragingRatingForMovies().get(m2));
    }

    @Test
    void getAveragingRatingForMovies_ReturnsException_WhenMoviesIsEmpty() {
        assertThrows(NoSuchElementException.class, () -> targetClass.getAveragingRatingForMovies());
    }
}
