package hm222yj_2dv515.a1.backend.controller.euclideanandpearson.euclideanandpearsoncontroller;

import hm222yj_2dv515.a1.backend.model.recommendedmovie.RecommendedMovie;
import hm222yj_2dv515.a1.backend.service.euclideanandpearsonservice.EuclideanAndPearsonService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/euclidean")
@CrossOrigin(origins = "http://localhost:5173") // Här berättar vi att vi litar på localhost, vår server körs på annan
                                                // port
public class EuclideanAndPearson {

    EuclideanAndPearsonService euclideanAndPearsonService;

    public EuclideanAndPearson(EuclideanAndPearsonService euclideanAndPearsonService) {
        this.euclideanAndPearsonService = euclideanAndPearsonService;
    }

    @GetMapping("/recommendations")
    public List<RecommendedMovie> getRecommendations(
            @RequestParam("userId") int userId,
            @RequestParam(value = "results", defaultValue = "3") int results,
            @RequestParam("isEuclidean") boolean isEuclidean) {

        return euclideanAndPearsonService.topRecommendations(userId, results, isEuclidean);
    }
}
