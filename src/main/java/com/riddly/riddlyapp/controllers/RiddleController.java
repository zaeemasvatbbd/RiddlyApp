package com.riddly.riddlyapp.controllers;

import com.riddly.riddlyapp.models.Riddle;
import com.riddly.riddlyapp.repositories.RiddleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("riddly/")
public class RiddleController {

    RiddleRepository riddleRepository;

    private RiddleController(RiddleRepository riddleRepository) { this.riddleRepository = riddleRepository; }

    @GetMapping("/riddles")
    public ResponseEntity<List<Riddle>> getRiddles() {
        List<Riddle> riddles = riddleRepository.findAll();
        return riddles == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(riddles);
    }


}
