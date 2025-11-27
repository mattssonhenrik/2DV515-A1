package hm222yj_2dv515.a1.backend.model.userandmovieset;

import java.util.Map;

import hm222yj_2dv515.a1.backend.model.usermodel.UserModel;
import hm222yj_2dv515.a1.backend.model.moviemodel.MovieModel;

public class UserAndMovieSet {
    Map<Integer, UserModel> users;
    Map<Integer, MovieModel> movies;
    // Vår wrapper för att para ihop användare och filmer.

    public UserAndMovieSet(Map<Integer, UserModel> users, Map<Integer, MovieModel> movies) {
        this.users = users;
        this.movies = movies;
    }

    public Map<Integer, UserModel> getUsers() {
        return users;
    }

    public Map<Integer, MovieModel> getMovies() {
        return movies;
    }
}
