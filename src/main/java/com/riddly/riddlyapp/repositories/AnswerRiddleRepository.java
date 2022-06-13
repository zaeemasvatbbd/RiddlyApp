package com.riddly.riddlyapp.repositories;

import com.riddly.riddlyapp.models.AnswerRiddle;
import com.riddly.riddlyapp.models.Player;
import com.riddly.riddlyapp.models.Riddle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRiddleRepository extends CrudRepository<AnswerRiddle, String> {
    List<AnswerRiddle> findAll();

//    List<AnswerRiddle> findByPlayer(Player player);


}
