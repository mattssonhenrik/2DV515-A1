package hm222yj_2dv515.a1.backend.model.datareader;

// Simple DTO class, no real "bussiness logic", just support for dataController getData().
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

    @Override
    public String toString() {
        return "DataReader{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", rating=" + rating +
                '}';
    }
}
