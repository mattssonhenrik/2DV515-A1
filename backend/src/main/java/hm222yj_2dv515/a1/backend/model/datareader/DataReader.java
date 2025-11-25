package hm222yj_2dv515.a1.backend.model.datareader;

public class DataReader {
    int userId;
    String userName;
    int movieId;
    String movieTitle;
    double rating;

    public DataReader(int userId, String userName, int movieId, String movieTitle, double rating) {
        this.userId = userId;
        this.userName = userName;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public double getRating() {
        return rating;
    }
}
