package hm222yj_2dv515.a1.backend.model.recommendedmovie;

public class RecommendedMovie {
    private final int id;
    private final String title;
    private final double score;

    public RecommendedMovie(int id, String title, double score) {
        this.id = id;
        this.title = title;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "RecommendedMovie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", score=" + score +
                '}';
    }
}
