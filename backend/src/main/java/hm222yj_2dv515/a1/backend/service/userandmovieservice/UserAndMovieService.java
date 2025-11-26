package hm222yj_2dv515.a1.backend.service.userandmovieservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import hm222yj_2dv515.a1.backend.service.dataservice.DataService;
import hm222yj_2dv515.a1.backend.model.datareader.DataReader;
import hm222yj_2dv515.a1.backend.model.moviemodel.MovieModel;
import hm222yj_2dv515.a1.backend.model.userandmovieset.UserAndMovieSet;
import hm222yj_2dv515.a1.backend.model.usermodel.UserModel;

@Service
public class UserAndMovieService {
    DataService dataService;

    public UserAndMovieService(DataService dataService) {
        this.dataService = dataService;
    }

    public UserAndMovieSet buildData() {
        ArrayList<DataReader> rows = dataService.getData();

        Map<Integer, UserModel> users = new HashMap<>();
        Map<Integer, MovieModel> movies = new HashMap<>();

        for (DataReader row : rows) {
            int userId = row.getUserId();
            String userName = row.getUserName();
            int movieId = row.getMovieId();
            String movieTitle = row.getMovieTitle();
            Double rating = row.getRating();

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
