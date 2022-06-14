package com.riddly.riddlyapp.controllers;

import com.riddly.riddlyapp.models.AnsweredRiddle;
import com.riddly.riddlyapp.models.Player;
import com.riddly.riddlyapp.models.Riddle;
import com.riddly.riddlyapp.repositories.AnsweredRiddleRepository;
import com.riddly.riddlyapp.repositories.PlayerRepository;
import com.riddly.riddlyapp.repositories.RiddleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Auth applied on any endpoint (/**)
@RestController
@RequestMapping("riddly/riddles/answered")
public class AnsweredRiddleController {

    AnsweredRiddleRepository answerRiddleRepository;
    PlayerRepository playerRepository;
    RiddleRepository riddleRepository;

    private AnsweredRiddleController(
            AnsweredRiddleRepository answerRiddleRepository,
            PlayerRepository playerRepository,
            RiddleRepository riddleRepository
    ) {
        this.answerRiddleRepository = answerRiddleRepository;
        this.playerRepository = playerRepository;
        this.riddleRepository = riddleRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<AnsweredRiddle>> getAnsweredRiddles(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer riddleID
    ) {

        if (username != null) {
            Optional<Player> playerOptional = Optional.ofNullable(playerRepository.findByUsername(username));
            if (playerOptional.isEmpty()) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(answerRiddleRepository.findByAnsweredRiddleIdPlayer(playerOptional.get()));
        }

        if (riddleID != null) {
            Optional<Riddle> riddleOptional = riddleRepository.findById(riddleID);
            if (riddleOptional.isEmpty()) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(answerRiddleRepository.findByAnsweredRiddleIdRiddle(riddleOptional.get()));
        }

        List<AnsweredRiddle> answeredRiddles = answerRiddleRepository.findAll();
        return answeredRiddles == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(answeredRiddles);
    }

    @PostMapping("")
    public ResponseEntity<String> addAnsweredRiddle(@RequestBody AnsweredRiddle answerRiddle){
        if (answerRiddle == null) return ResponseEntity.badRequest().build();
        answerRiddleRepository.save(answerRiddle);
        return ResponseEntity.ok("User answered riddle saved successfully");
    }

}
