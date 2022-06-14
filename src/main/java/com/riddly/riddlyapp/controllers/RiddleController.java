package com.riddly.riddlyapp.controllers;

import com.riddly.riddlyapp.models.AnsweredRiddle;
import com.riddly.riddlyapp.models.Player;
import com.riddly.riddlyapp.models.Riddle;
import com.riddly.riddlyapp.repositories.AnsweredRiddleRepository;
import com.riddly.riddlyapp.repositories.PlayerRepository;
import com.riddly.riddlyapp.repositories.RiddleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("riddly/")
public class RiddleController {

    RiddleRepository riddleRepository;
    PlayerRepository playerRepository;
    AnsweredRiddleRepository answeredRiddleRepository;

    private RiddleController(
            PlayerRepository playerRepository,
            RiddleRepository riddleRepository,
            AnsweredRiddleRepository answeredRiddleRepository) {
        this.playerRepository = playerRepository;
        this.riddleRepository = riddleRepository;
        this.answeredRiddleRepository = answeredRiddleRepository;
    }

    @GetMapping("/riddles")
    public ResponseEntity<List<Riddle>> getRiddles() {
        List<Riddle> riddles = riddleRepository.findAll();
        return riddles == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(riddles);
    }

    @GetMapping("/riddle/")
    public ResponseEntity<Riddle> getRandomRiddleForUser(@RequestParam String username) {

        Player player = playerRepository.findByUsername(username);
        if (player == null) return ResponseEntity.badRequest().build();

        List<AnsweredRiddle> answeredRiddles = answeredRiddleRepository.findByAnsweredRiddleIdPlayer(player);

        if (answeredRiddles == null) return ResponseEntity.badRequest().build();

        ArrayList<Riddle> filteredRiddles = new ArrayList<>();
        answeredRiddles.stream()
                .filter(answeredRiddle -> answeredRiddle.getAnsweredRiddleId().getPlayer().getUsername().equals(player.getUsername()))
                .map(answeredRiddle -> filteredRiddles.add(answeredRiddle.getAnsweredRiddleId().getRiddle()));

        return ResponseEntity.ok( filteredRiddles.isEmpty() ? new Riddle() : filteredRiddles.get(new Random().nextInt(filteredRiddles.size())));

    }


}
