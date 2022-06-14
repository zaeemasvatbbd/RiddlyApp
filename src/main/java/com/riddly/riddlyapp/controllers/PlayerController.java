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
        List<Player> players = playerRepository.findByUsername(username);
        return players == null ? ResponseEntity.badRequest().build() :
               players.size() <= 0 ? ResponseEntity.notFound().build() :
               ResponseEntity.ok(players.get(0));
    }

    @PostMapping("")
    public ResponseEntity<String> addPlayer(@RequestBody Player player) {
        if (player == null) return ResponseEntity.badRequest().build();
        playerRepository.save(player);
        return ResponseEntity.ok(String.format("Player %s saved successfully", player));
    }

    @PatchMapping("/{username}")
    public ResponseEntity<String> updatePlayerPoints(
            @PathVariable String username,
            @RequestBody updatePlayerPointsPayload payload) {

        List<Player> players = playerRepository.findByUsername(username);

        if (players == null)
            return ResponseEntity.badRequest().build();

        if (players.size() <= 0)
            return ResponseEntity.notFound().build();

        Player player = players.get(0);

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
