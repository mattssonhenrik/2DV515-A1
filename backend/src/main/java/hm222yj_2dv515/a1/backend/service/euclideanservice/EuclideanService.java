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

    public List<RecommendedMovie> topRecommendations(int targetUserId, int number, boolean isEuclidean) {
        UserAndMovieSet data = userAndMovieService.buildData();
        Map<Integer, MovieModel> movies = data.getMovies();
        Map<Integer, Double> scores = computeWeightedMovieScores(targetUserId, isEuclidean); // Computeweightedmoviescore
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

    public Map<Integer, Double> computeWeightedMovieScores(int targetUserId, boolean isEuclidean) {
        UserAndMovieSet data = userAndMovieService.buildData();
        Map<Integer, UserModel> users = data.getUsers();
        UserModel targetUser = users.get(targetUserId);
        Map<Integer, Double> similarities = similarityScoreToAllUsers(targetUserId, users, isEuclidean); // SimilarityScoretoallusers
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

    // COMPARE TO OTHER USERS
    public Map<Integer, Double> similarityScoreToAllUsers(int targetUserId, Map<Integer, UserModel> users,
            boolean isEuclidean) {
        UserModel targetUser = users.get(targetUserId);
        Map<Integer, Double> similarities = new HashMap<>();
        // https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html
        for (Map.Entry<Integer, UserModel> userEntry : users.entrySet()) {
            int otherUserId = userEntry.getKey();
            if (otherUserId == targetUserId) {
                continue;
            }
            UserModel otherUser = userEntry.getValue();
            Double similarityComparison;
            if (isEuclidean == true) {
                similarityComparison = euclideanSimilarity(targetUser, otherUser);
            } else {
                similarityComparison = pearsonSimilarity(targetUser, otherUser);
            }

            if (similarityComparison > 0.0) {
                similarities.put(otherUserId, similarityComparison);
            }
        }
        return similarities;
    }

    // EUCLIDEAN SCORE
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

    // PEARSON SCORE
    public Double pearsonSimilarity(UserModel u1, UserModel u2) {
        Map<Integer, Double> r1 = u1.getRatings();
        Map<Integer, Double> r2 = u2.getRatings();
        Double sum1 = 0.0;
        Double sum2 = 0.0;
        Double sum1Square = 0.0;
        Double sum2Square = 0.0;
        Double productSum = 0.0;
        int numberOfCommonMovies = 0;

        for (int id : r1.keySet()) {
            if (r2.containsKey(id)) {
                Double val1 = r1.get(id);
                Double val2 = r2.get(id);
                sum1 += val1;
                sum2 += val2;
                sum1Square += Math.pow(val1, 2);
                sum2Square += Math.pow(val2, 2);
                productSum += val1 * val2;
                numberOfCommonMovies++;
            }
        }
        if (numberOfCommonMovies == 0) {
            return 0.0;
        }
        Double numeratorNumber = productSum - (sum1 * sum2 / numberOfCommonMovies);
        Double denominatorNumber = Math.sqrt(
                (sum1Square - Math.pow(sum1, 2) / numberOfCommonMovies) * (sum2Square - Math.pow(sum2, 2) / numberOfCommonMovies));
        if (denominatorNumber == 0) {
            return 0.0;
        } else {
            return numeratorNumber / denominatorNumber;
        }
    }
}
