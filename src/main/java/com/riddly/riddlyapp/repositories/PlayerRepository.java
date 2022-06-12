package com.riddly.riddlyapp.repositories;

import com.riddly.riddlyapp.models.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    List<Player> findByUsername(String username);
    List<Player> findByEmail(String email);
    List<Player> findAll();

}
