package hm222yj_2dv515.a1.backend.controller.datacontroller;

// https://www.geeksforgeeks.org/postgresql/postgresql-jdbc-driver/
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

public class DataController {
    String smallOrLarge;
    String url = "jdbc:postgresql://localhost:5432/1DV515_A1";
    String user = "postgres";
    String password = "root";

    public DataController(String fileType) {
        this.smallOrLarge = fileType;
    }

    private Statement connectAndGetStatement() {
        try {
            Connection jdbcconnection = DriverManager.getConnection(url, user, password);
            Statement statement = jdbcconnection.createStatement();
            return statement;
        } catch (Exception error) {
            System.out.println(error);
        }
        return null;
    }

    public void loadData(String smallOrLarge) {
        Statement statement = connectAndGetStatement();
        String sqlQury = "";
    }
}