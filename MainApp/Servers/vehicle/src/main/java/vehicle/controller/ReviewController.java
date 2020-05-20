package vehicle.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api")
public class ReviewController {

    @GetMapping(path = "/review",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAll() {

        return new ResponseEntity<>("review", HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/vehicle/{vehicleId}/review",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get(@PathVariable String vehicleId) {

        return new ResponseEntity<>(vehicleId, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/vehicle/{vehicleId}/review",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createNew(@PathVariable String vehicleId, @RequestBody String reviewDTO) {

        return new ResponseEntity<>(reviewDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/vehicle/{vehicleId}/review/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOne(@PathVariable String vehicleId, @PathVariable String id) {

        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/vehicle/{vehicleId}/review/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@PathVariable String vehicleId, @PathVariable String id, @RequestBody String vehicleDTO) {

        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/vehicle/{vehicleId}/review/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable String vehicleId, @PathVariable String id) {

        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }
}
