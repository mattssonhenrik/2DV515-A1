package hm222yj_2dv515.a1.backend.controller.datacontroller;

import hm222yj_2dv515.a1.backend.model.usermodel.UserModel;
import hm222yj_2dv515.a1.backend.service.dataservice.DataService;
import hm222yj_2dv515.a1.backend.service.userandmovieservice.UserAndMovieService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "http://localhost:5173") // Här berättar vi att vi litar på localhost, vår server körs på annan
                                                // port

public class DataController {
    DataService dataService;
    UserAndMovieService userAndMovieService;

    public DataController(DataService dataService, UserAndMovieService userAndMovieService) {
        this.dataService = dataService;
        this.userAndMovieService = userAndMovieService;
    }

    @PostMapping("/reload")
    public ResponseEntity<String> reloadData() {
        dataService.deleteData();
        dataService.loadData();
        return ResponseEntity.ok("Data reloaded");
    }

@GetMapping("/users")
    public List<UserModel> getAllUsers() {
        return new ArrayList<>(userAndMovieService.buildData().getUsers().values());
    }
}
