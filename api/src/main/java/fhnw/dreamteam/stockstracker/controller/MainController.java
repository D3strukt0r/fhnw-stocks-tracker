package fhnw.dreamteam.stockstracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping(path = "/")
    public ResponseEntity<Object> getRoot() {
        return ResponseEntity.accepted().body("Hello, World!");
    }
}
