package hm222yj_2dv515.a1.backend.model.usermodel;

import java.util.HashMap;
import java.util.Map;

public class UserModel {
    int id;
    String name;
    Map<Integer, Double> ratings = new HashMap<>();
    // Behövs för att beräkna euclidean distans. 

    public UserModel (int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Double> getRatings() {
        return ratings;
    }
}
