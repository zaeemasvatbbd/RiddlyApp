package com.riddly.riddlyapp.controllers;

import com.riddly.riddlyapp.models.AnsweredRiddle;
import com.riddly.riddlyapp.models.Player;
import com.riddly.riddlyapp.models.Riddle;
import com.riddly.riddlyapp.repositories.AnsweredRiddleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Auth applied on any endpoint (/**)
@RestController
@RequestMapping("riddly/riddles/answered")
public class AnsweredRiddleController {

    AnsweredRiddleRepository answerRiddleRepository;

    @GetMapping("")
    public ResponseEntity<List<AnsweredRiddle>> getAnsweredRiddles(
            @RequestParam(required = false) Player player,
            @RequestParam(required = false) Riddle riddle
    ) {

        List<AnsweredRiddle> answeredRiddles =
                player != null ? answerRiddleRepository.findByAnsweredRiddleIdPlayer(player) :
                riddle != null ? answerRiddleRepository.findByAnsweredRiddleIdRiddle(riddle) :
                answerRiddleRepository.findAll();

        return answeredRiddles == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(answeredRiddles);
    }

    @PostMapping("")
    public ResponseEntity<String> addAnsweredRiddle(@RequestBody AnsweredRiddle answerRiddle){
        if (answerRiddle == null) return ResponseEntity.badRequest().build();
        answerRiddleRepository.save(answerRiddle);
        return ResponseEntity.ok("User answered riddle saved successfully");
    }

}
