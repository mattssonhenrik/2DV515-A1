package hm222yj_2dv515.a1.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hm222yj_2dv515.a1.backend.controller.datacontroller.DataController;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		DataController datacontroller = new DataController("small");
		datacontroller.loadData();
	}

}
