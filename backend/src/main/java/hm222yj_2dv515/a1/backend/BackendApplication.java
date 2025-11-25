package hm222yj_2dv515.a1.backend;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hm222yj_2dv515.a1.backend.controller.datacontroller.DataController;
import hm222yj_2dv515.a1.backend.model.datareader.DataReader;


@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		DataController dataController = new DataController("large"); // Pass argumeter small or large
		// dataController.loadData();
		// dataController.deleteData();
		ArrayList<DataReader> results = new ArrayList<>(dataController.getData());
		System.out.println(results);
	}

}
