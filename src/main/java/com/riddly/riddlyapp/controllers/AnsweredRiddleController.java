package com.riddly.riddlyapp.controllers;

import com.riddly.riddlyapp.models.AnswerRiddleKey;
import com.riddly.riddlyapp.models.AnsweredRiddle;
import com.riddly.riddlyapp.models.Player;
import com.riddly.riddlyapp.models.Riddle;
import com.riddly.riddlyapp.repositories.AnsweredRiddleRepository;
import com.riddly.riddlyapp.repositories.PlayerRepository;
import com.riddly.riddlyapp.repositories.RiddleRepository;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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

    @Data
    static class addAnsweredRiddlePayload {
        String username;
        Integer riddleID;
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
    public ResponseEntity<String> addAnsweredRiddle(
            @RequestBody addAnsweredRiddlePayload payload
    ) {

        Player player = playerRepository.findByUsername(payload.username);
        if (player == null) return ResponseEntity.badRequest().build();

        Optional<Riddle> riddle = riddleRepository.findById(payload.riddleID);
        if(riddle.isEmpty()) return ResponseEntity.badRequest().build();


        AnswerRiddleKey answerRiddleKey = new AnswerRiddleKey();
        answerRiddleKey.setRiddle(riddle.get());
        answerRiddleKey.setPlayer(player);

        AnsweredRiddle answeredRiddle = new AnsweredRiddle();
        answeredRiddle.setAnsweredRiddleId(answerRiddleKey);
        answerRiddleRepository.save(answeredRiddle);

        return ResponseEntity.ok(String.format("%s's attempt for the riddle '%s' has been recorded!", payload.username, riddle.get().getRiddleDescription()));
    }

}
