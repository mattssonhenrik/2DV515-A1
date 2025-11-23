package hm222yj_2dv515.a1.backend.controller.datacontroller;

// https://www.geeksforgeeks.org/postgresql/postgresql-jdbc-driver/
// https://www.postgresql.org/docs/current/sql-copy.html
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

public class DataController {
    String smallOrLarge;
    String url = "jdbc:postgresql://localhost:5432/1DV515_A1";
    String user = "postgres";
    String password = "root";
    String projectDirectory = System.getProperty("user.dir");

    public DataController(String fileType) {
        this.smallOrLarge = fileType;
        System.out.println("This is the root directory of the project: " + projectDirectory);
    }

    private Statement connectAndGetStatement() {
        try {
            Connection jdbcconnection = DriverManager.getConnection(url, user, password);
            Statement statement = jdbcconnection.createStatement();
            return statement;
        } catch (Exception error) {
            System.out.println("Soemthing went wrong in connectAndGetStatement!");
            error.printStackTrace();
        }
        return null;
    }

    private String getPath(String type) {
        return projectDirectory + "/data/" + smallOrLarge + "-" + type + "/" + type + ".csv";
    }

    public void loadData() {
        try {
            Statement statement = connectAndGetStatement();
            String movieFilePath = getPath("movies");
            String userFilePath = getPath("users");
            String ratingFilePath = getPath("ratings");
            statement.executeUpdate("COPY movies FROM '" + movieFilePath +"'" + " DELIMITER ',' CSV HEADER;");
            statement.executeUpdate("COPY users FROM '" + userFilePath +"'" + " DELIMITER ',' CSV HEADER;");
            statement.executeUpdate("COPY ratings FROM '" + ratingFilePath +"'" + " DELIMITER ',' CSV HEADER;");
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