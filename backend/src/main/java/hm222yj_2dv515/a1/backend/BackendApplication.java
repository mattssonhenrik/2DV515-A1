package hm222yj_2dv515.a1.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import hm222yj_2dv515.a1.backend.model.datareader.DataReader;
import hm222yj_2dv515.a1.backend.model.recommendedmovie.RecommendedMovie;
import hm222yj_2dv515.a1.backend.service.dataservice.DataService;
import hm222yj_2dv515.a1.backend.service.euclideanandpearsonservice.EuclideanAndPearsonService;
import hm222yj_2dv515.a1.backend.service.userandmovieservice.UserAndMovieService;


@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		// DataService dataService = new DataService(); // Pass argumeter small or large // No need anymore, change in DataService.java instead.
		// dataService.deleteData();
		// dataService.loadData();
		// // ArrayList<DataReader> results = new ArrayList<>(dataController.getData());
		// // System.out.println(results);
		// UserAndMovieService userAndMovieService = new UserAndMovieService(dataService);
		// EuclideanService euclideanService = new EuclideanService(userAndMovieService);
		// List<RecommendedMovie> recommendations = euclideanService.topRecommendations(4, 3);
		// System.out.println(recommendations);
	}


}
