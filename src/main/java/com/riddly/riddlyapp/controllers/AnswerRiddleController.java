package com.riddly.riddlyapp.controllers;

import com.riddly.riddlyapp.models.AnswerRiddle;
import com.riddly.riddlyapp.models.Player;
import com.riddly.riddlyapp.models.Riddle;
import com.riddly.riddlyapp.repositories.AnswerRiddleRepository;
import com.riddly.riddlyapp.repositories.RiddleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("riddly/riddles/answered")
public class AnswerRiddleController {

    AnswerRiddleRepository answerRiddleRepository;

    @GetMapping("")
    public ResponseEntity<List<AnswerRiddle>> getAnsweredRiddles() {
        List<AnswerRiddle> answeredRiddles = answerRiddleRepository.findAll();

        if (answeredRiddles == null){
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok(answeredRiddles);
    }

    @GetMapping("/user")
    public ResponseEntity<List<AnswerRiddle>> getUserAnsweredRiddles(@RequestBody(required = false) Player player){
        if(player != null) {
            return ResponseEntity.ok(answerRiddleRepository.findByPlayer(player));
        } else {
            return  ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<String> addAnsweredRiddle(@RequestBody AnswerRiddle answerRiddle){
        if(answerRiddle == null) return ResponseEntity.badRequest().build();
        answerRiddleRepository.save(answerRiddle);
        return ResponseEntity.ok("User answered riddle saved successfully");
    }

}
