package vehicle.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api")
public class FuelController {

    @GetMapping(path = "/fuel",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAll() {

        return new ResponseEntity<>("fuel", HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/fuel",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createNew(@RequestBody String fuelDTO) {

        return new ResponseEntity<>(fuelDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/fuel/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOne(@PathVariable String id) {

        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/fuel/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody String fuelDTO) {

        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/fuel/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable String id) {

        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }
}
