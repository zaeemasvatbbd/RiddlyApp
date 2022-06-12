package com.riddly.riddlyapp.controllers;

import com.riddly.riddlyapp.models.Player;
import com.riddly.riddlyapp.models.Riddle;
import com.riddly.riddlyapp.repositories.PlayerRepository;
import com.riddly.riddlyapp.repositories.RiddleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("riddly/players")
public class PlayerController {

    PlayerRepository playerRepository;

    @GetMapping("")
    public ResponseEntity<List<Player>> getPlayer(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email
    ) {

        if (username != null)
            return ResponseEntity.ok(playerRepository.findByUsername(username));

        if (email != null)
            return ResponseEntity.ok(playerRepository.findByEmail(email));

        return ResponseEntity.ok(playerRepository.findAll());

    }

    @PostMapping("")
    public ResponseEntity<String> addPlayer(@RequestBody Player player) {
        if (player == null) return ResponseEntity.badRequest().build();
        playerRepository.save(player);
        return ResponseEntity.ok(String.format("Rental %s saved successfully", player));
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> updatePlayerPoints(
            @PathVariable Integer id,
            @RequestParam Boolean hasAnsweredCorrectly,
            @RequestParam Integer numAttempts,
            @RequestParam Long timeTaken) {

        Player player = playerRepository.findAll().get(id);
        if (player == null)
            return ResponseEntity.badRequest().build();

        final int basePoints = 100;

        if (hasAnsweredCorrectly) {
            long addedPoints = basePoints / numAttempts + basePoints / timeTaken;
            player.setPoints(player.getPoints() + addedPoints);
            playerRepository.save(player);
            return ResponseEntity.ok(String.format("Player has gotten %d points!", addedPoints));
        }

        return ResponseEntity.ok("Player did not get any points");
    }


}
