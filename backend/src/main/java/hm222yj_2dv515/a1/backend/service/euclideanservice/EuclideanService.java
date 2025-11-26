package hm222yj_2dv515.a1.backend.service.euclideanservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hm222yj_2dv515.a1.backend.service.userandmovieservice.UserAndMovieService;
import hm222yj_2dv515.a1.backend.model.moviemodel.MovieModel;
import hm222yj_2dv515.a1.backend.model.recommendedmovie.RecommendedMovie;
import hm222yj_2dv515.a1.backend.model.userandmovieset.UserAndMovieSet;
import hm222yj_2dv515.a1.backend.model.usermodel.UserModel;

@Service
public class EuclideanService {

    UserAndMovieService userAndMovieService;

    public EuclideanService(UserAndMovieService userAndMovieService) {
        this.userAndMovieService = userAndMovieService;
    }

    // COMPARE TO OTHER USERS
    public Map<Integer, Double> similarityScoreToAllUsers(int targetUserId, Map<Integer, UserModel> users) {
        UserModel targetUser = users.get(targetUserId);

        Map<Integer, Double> similarities = new HashMap<>();

        // https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html
        for (Map.Entry<Integer, UserModel> userEntry : users.entrySet()) {
            int otherUserId = userEntry.getKey();
            if (otherUserId == targetUserId) {
                continue;
            }
            UserModel otherUser = userEntry.getValue();
            Double similarityComparison = euclideanSimilarity(targetUser, otherUser); // invoke / call Eulicdeansimilarity

            if (similarityComparison > 0.0) {
                similarities.put(otherUserId, similarityComparison);
            }
        }
        return similarities;
    }

    // ACTUAL COMPARISON OF EUCLIDEAN SCORE
    public Double euclideanSimilarity(UserModel userA, UserModel userB) {
        Map<Integer, Double> ratingsA = userA.getRatings();
        Map<Integer, Double> ratingsB = userB.getRatings();

        Double sumOfSquares = 0.0;
        int commonCount = 0;

        for (Map.Entry<Integer, Double> ratingEntry : ratingsA.entrySet()) {
            int movieId = ratingEntry.getKey();
            Double ratingA = ratingEntry.getValue();

            if (ratingsB.containsKey(movieId)) {
                Double ratingB = ratingsB.get(movieId);
                Double difference = ratingA - ratingB;
                sumOfSquares += difference * difference;
                commonCount++;
            }
        }
        if (commonCount == 0) {
            return 0.0;
        }
        return 1.0 / (1.0 + (sumOfSquares));
    }

    public Map<Integer, Double> computeWeightedMovieScores(int targetUserId) {
        UserAndMovieSet data = userAndMovieService.buildData();
        Map<Integer, UserModel> users = data.getUsers();

        UserModel targetUser = users.get(targetUserId);

        Map<Integer, Double> similarities = similarityScoreToAllUsers(targetUserId, users); // Invoke / call SimilarityScoretoallusers
        Map<Integer, Double> targetRatings = targetUser.getRatings();

        Map<Integer, Double> weightedSum = new HashMap<>();
        Map<Integer, Double> similaritySum = new HashMap<>();

        for (Map.Entry<Integer, Double> simEntry : similarities.entrySet()) {
            int otherUserId = simEntry.getKey();
            Double similarity = simEntry.getValue();

            UserModel otherUser = users.get(otherUserId);

            for (Map.Entry<Integer, Double> ratingEntry : otherUser.getRatings().entrySet()) {
                int movieId = ratingEntry.getKey();
                Double rating = ratingEntry.getValue();

                if (targetRatings.containsKey(movieId)) {
                    continue;
                }

                Double weightedRating = similarity * rating;

                // Accumulera för sumberäkningar
                Double currentWeighted = weightedSum.get(movieId);
                if (currentWeighted == null) {
                    weightedSum.put(movieId, weightedRating);
                } else {
                    weightedSum.put(movieId, currentWeighted + weightedRating);
                }

                // Accumulera för sumberäkningar
                Double currentSimilarity = similaritySum.get(movieId);
                if (currentSimilarity == null) {
                    similaritySum.put(movieId, similarity);
                } else {
                    similaritySum.put(movieId, currentSimilarity + similarity);
                }
            }
        }
        Map<Integer, Double> weightedScores = new HashMap<>();

        for (Map.Entry<Integer, Double> entry : weightedSum.entrySet()) {
            int movieId = entry.getKey();
            double sumWeighted = entry.getValue();
            double sumSimilarity = similaritySum.getOrDefault(movieId, 0.0);

            if (sumSimilarity > 0.0) {
                double score = sumWeighted / sumSimilarity;
                weightedScores.put(movieId, score);
            }
        }
        return weightedScores;
    }

    public List<RecommendedMovie> topRecommendations(int targetUserId, int number) {
        UserAndMovieSet data = userAndMovieService.buildData();
        Map<Integer, MovieModel> movies = data.getMovies();
        Map<Integer, Double> scores = computeWeightedMovieScores(targetUserId); // invoke // call
                                                                                // computeweightedmoviescore method

        // Här gör vi om till en lsita för att kunna sortera smidigt med .sort().
        List<Map.Entry<Integer, Double>> sortedList = new ArrayList<>(scores.entrySet());
        sortedList.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));

        List<RecommendedMovie> result = new ArrayList<>(); // Loopar genom en sortedlist och plockar ut topvalen
        for (int i = 0; i < sortedList.size() && i < number; i++) {
            Map.Entry<Integer, Double> entry = sortedList.get(i);
            int movieId = entry.getKey();
            double score = entry.getValue();

            MovieModel movie = movies.get(movieId);
            String title = (movie != null) ? movie.getTitle() : "Unknown Title";

            result.add(new RecommendedMovie(movieId, title, score));
        }

        return result;

    }
}
