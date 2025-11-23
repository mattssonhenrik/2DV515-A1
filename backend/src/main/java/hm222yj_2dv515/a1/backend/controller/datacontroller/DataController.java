package hm222yj_2dv515.a1.backend.controller.datacontroller;

// https://www.geeksforgeeks.org/postgresql/postgresql-jdbc-driver/
// https://www.postgresql.org/docs/current/sql-copy.html
import java.sql.DriverManager;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.io.Reader;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class DataController {
    String smallOrLarge;
    String url = "jdbc:postgresql://localhost:5432/2DV515_A1";
    String user = "postgres";
    String password = "root";
    String projectDirectory = System.getProperty("user.dir");

    public DataController(String fileType) {
        this.smallOrLarge = fileType;
        System.out.println("This is the root directory of the project: " + projectDirectory);
    }

    // private Statement connectAndGetStatement() {
    // try {
    // Connection jdbcconnection = DriverManager.getConnection(url, user, password);
    // Statement statement = jdbcconnection.createStatement();
    // return statement;
    // } catch (Exception error) {
    // System.out.println("Soemthing went wrong in connectAndGetStatement!");
    // error.printStackTrace();
    // }
    // return null;
    // }

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

    // public void deleteData() {
    // Statement statement = connectAndGetStatement();
    // String sqlQuery = "";
    // }
}