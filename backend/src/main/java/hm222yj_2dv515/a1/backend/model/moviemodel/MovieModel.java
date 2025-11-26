package hm222yj_2dv515.a1.backend.model.moviemodel;

public class MovieModel {
    int id;
    String title;
    // Behöver id och namn sedan för rekommendationerna vi ska ge

    public MovieModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId(){
        return id;
    }

    public String getTitle () {
        return title;
    }
    
}
