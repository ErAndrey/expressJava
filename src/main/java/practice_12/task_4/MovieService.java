package practice_12.task_4;

import java.util.*;
import java.util.stream.Collectors;

public class MovieService<T extends Number> {
    private final Map<Movie, List<Rating<T>>> movies;

    public MovieService() {
        this.movies = new HashMap<>();
    }

    public Map<Movie, List<Rating<T>>> getMovies() {
        return this.movies;
    }

    public synchronized void addRatingToMovie(Movie movie, Rating<T> rating) {
        if (rating == null || movie == null) {
            throw new IllegalArgumentException("Входные данные null");
        }
        if (!(rating.rating().doubleValue() >= 1 && rating.rating().doubleValue() <= 10.0)) {
            throw new IllegalArgumentException("Рейтинг должен быть в диапазоне [1, 10]");
        }
        if (!this.movies.containsKey(movie)) {
            this.movies.put(movie, new ArrayList<>(List.of(rating)));
            return;
        }
        List<Rating<T>> movieRatingList = this.movies.get(movie);
        movieRatingList.add(rating);
        this.movies.put(movie, movieRatingList);
    }

    public Map<Movie, Double> getAveragingRatingForMovies() {
        if (this.movies.isEmpty()) {
            throw new NoSuchElementException("В сервисе нет фильмов");
        }
        return this.movies.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            double average = entry.getValue()
                                    .stream()
                                    .mapToDouble(r -> r.rating().doubleValue())
                                    .average()
                                    .orElse(0.0);
                            return Math.round(average * 100.0) / 100.0;
                        }));
    }
}
