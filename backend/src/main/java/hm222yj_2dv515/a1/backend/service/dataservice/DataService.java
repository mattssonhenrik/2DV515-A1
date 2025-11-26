package hm222yj_2dv515.a1.backend.service.dataservice;

// https://www.geeksforgeeks.org/postgresql/postgresql-jdbc-driver/
// https://www.postgresql.org/docs/current/sql-copy.html
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.Reader;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.stereotype.Service;

import hm222yj_2dv515.a1.backend.model.datareader.DataReader;

@Service
public class DataService {
    String smallOrLarge;
    String url = "jdbc:postgresql://localhost:5432/2DV515_A1";
    String user = "postgres";
    String password = "root";
    String projectDirectory = System.getProperty("user.dir");

    public DataService() {
        this.smallOrLarge = "large";
        System.out.println("This is the root directory of the project: " + projectDirectory);
    }

    public ArrayList<DataReader> getData() {
        ArrayList<DataReader> dataBaseResponse = new ArrayList<DataReader>();
        String getDataSqlQuery = "SELECT " +
                "  users.user_id    AS user_id, " +
                "  users.name       AS user_name, " +
                "  movies.movie_id  AS movie_id, " +
                "  movies.title     AS movie_title, " +
                "  ratings.rating   AS rating " +
                "FROM ratings " +
                "JOIN users  ON ratings.user_id  = users.user_id " +
                "JOIN movies ON ratings.movie_id = movies.movie_id;";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(getDataSqlQuery)) {

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                int movieId = resultSet.getInt("movie_id");
                String movieTitle = resultSet.getString("movie_title");
                Double rating = resultSet.getDouble("rating");

                DataReader readLineToInsert = new DataReader(userId, userName, movieId, movieTitle, rating);
                dataBaseResponse.add(readLineToInsert);
            }

        } catch (SQLException error) {
            System.out.println("Something went wrong in getData method!");
            error.printStackTrace();
        }
        return dataBaseResponse;
    }

    //// CSV LOADING AND DELETING ////
    private String getPath(String type) {
        return projectDirectory + "/data/" + smallOrLarge + "-" + type + "/" + type + ".csv";
    }

    public void loadData() {
        try (Connection jdbcconnection = DriverManager.getConnection(url, user, password)) {
            CopyManager copyManager = new CopyManager((BaseConnection) jdbcconnection);

            // https://jdbc.postgresql.org/documentation/publicapi/org/postgresql/copy/CopyManager.html
            String movieFilePath = getPath("movies");
            try (Reader reader = new FileReader(movieFilePath)) {
                copyManager.copyIn(
                        "COPY movies FROM STDIN WITH (FORMAT csv, HEADER true)",
                        reader);
            }

            String userFilePath = getPath("users");
            try (Reader reader = new FileReader(userFilePath)) {
                copyManager.copyIn(
                        "COPY users FROM STDIN WITH (FORMAT csv, HEADER true)",
                        reader);
            }

            String ratingFilePath = getPath("ratings");
            try (Reader reader = new FileReader(ratingFilePath)) {
                copyManager.copyIn(
                        "COPY ratings FROM STDIN WITH (FORMAT csv, HEADER true)",
                        reader);
            }

        } catch (Exception error) {
            System.out.println("Something went wrong in loadData method!");
            error.printStackTrace();
        }

    }

    public void deleteData() {
        String deleteQuery = "TRUNCATE ratings, movies, users CASCADE;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteQuery);
        } catch (Exception error) {
            System.out.println("Something went wrong in deleteData method!");
            error.printStackTrace();
        }
    }
}
