package com.riddly.riddlyapp.controllers;

import com.riddly.riddlyapp.models.Player;
import com.riddly.riddlyapp.models.Riddle;
import com.riddly.riddlyapp.repositories.PlayerRepository;
import com.riddly.riddlyapp.repositories.RiddleRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("riddly/players")
public class PlayerController {

    PlayerRepository playerRepository;

    private PlayerController(PlayerRepository playerRepository) { this.playerRepository = playerRepository; }

    @Getter
    @Setter
    static class updatePlayerPointsPayload{

        Boolean hasAnsweredCorrectly;
        Integer numAttempts;
        Long timeTaken;

    }


    @GetMapping("")
    public ResponseEntity<List<Player>> getPlayers() {
        List<Player> players = playerRepository.findAll();
        return players == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(players);

    }

    @GetMapping("/{username}")
    public ResponseEntity<Player> getPlayer(@PathVariable String username) {
        Player player = playerRepository.findByUsername(username);
        return player == null ? ResponseEntity.badRequest().build() :
               ResponseEntity.ok(player);
    }

    @PostMapping("")
    public ResponseEntity<String> addPlayer(@RequestBody Player player) {
        if (player == null) return ResponseEntity.badRequest().build();
        player.setPoints(0);
        if(!playerRepository.findByUsername(player.getUsername()).isEmpty()){
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if(!playerRepository.findByEmail(player.getEmail()).isEmpty()){
            return ResponseEntity.badRequest().body("Email already exists");
        }
        playerRepository.save(player);
        return ResponseEntity.ok(String.format("Player %s saved successfully", player));
    }

    @PatchMapping("/{username}")
    public ResponseEntity<String> updatePlayerPoints(
            @PathVariable String username,
            @RequestBody updatePlayerPointsPayload payload) {

        Player player = playerRepository.findByUsername(username);

        if (player == null)
            return ResponseEntity.badRequest().build();

        if (payload.hasAnsweredCorrectly) {

            final int basePoints = 100;
            long addedPoints = basePoints / payload.numAttempts + basePoints / payload.timeTaken;
            player.setPoints(player.getPoints() + addedPoints);
            playerRepository.save(player);
            return ResponseEntity.ok(String.format("Player has gotten %d points!", addedPoints));
        }

        return ResponseEntity.ok("Player did not get any points");
    }


}
