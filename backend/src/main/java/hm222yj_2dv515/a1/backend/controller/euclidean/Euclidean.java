package hm222yj_2dv515.a1.backend.controller.euclidean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hm222yj_2dv515.a1.backend.controller.datacontroller.DataController;
import hm222yj_2dv515.a1.backend.model.datareader.DataReader;
import hm222yj_2dv515.a1.backend.model.moviemodel.MovieModel;
import hm222yj_2dv515.a1.backend.model.userandmovieset.UserAndMovieSet;
import hm222yj_2dv515.a1.backend.model.usermodel.UserModel;

public class Euclidean {
    DataController dataController;

    public Euclidean(DataController dataController) {
        this.dataController = dataController;
    }

    public UserAndMovieSet buildData() {
        ArrayList<DataReader> rows = dataController.getData();

        Map<Integer, UserModel> users = new HashMap<>();
        Map<Integer, MovieModel> movies = new HashMap<>();

        for (DataReader row : rows) {
            int userId = row.getUserId();
            String userName = row.getUserName();
            int movieId = row.getMovieId();
            String movieTitle = row.getMovieTitle();
            double rating = row.getRating();

            UserModel user = users.get(userId);
            if (user == null) {
                user = new UserModel(userId, userName);
                users.put(userId, user);
            }
            user.getRatings().put(movieId, rating);

            if (!movies.containsKey(movieId)) {
                movies.put(movieId, new MovieModel(movieId, movieTitle));
            }
        }

        return new UserAndMovieSet(users, movies);
    }
}
