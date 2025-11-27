package hm222yj_2dv515.a1.backend.controller.euclidean.euclideancontroller;

import hm222yj_2dv515.a1.backend.model.recommendedmovie.RecommendedMovie;
import hm222yj_2dv515.a1.backend.service.euclideanservice.EuclideanService;

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
public class EuclideanController {

    EuclideanService euclideanService;

    public EuclideanController(EuclideanService euclideanService) {
        this.euclideanService = euclideanService;
    }

    @GetMapping("/recommendations")
    public List<RecommendedMovie> getRecommendations(
            @RequestParam("userId") int userId,
            @RequestParam(value = "results", defaultValue = "3") int results,
            @RequestParam("isEuclidean") boolean isEuclidean) {

        return euclideanService.topRecommendations(userId, results, isEuclidean);
    }
}
