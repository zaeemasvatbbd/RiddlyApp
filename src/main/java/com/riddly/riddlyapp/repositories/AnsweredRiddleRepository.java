package com.riddly.riddlyapp.repositories;

import com.riddly.riddlyapp.models.AnsweredRiddle;
import com.riddly.riddlyapp.models.AnswerRiddleKey;
import com.riddly.riddlyapp.models.Player;
import com.riddly.riddlyapp.models.Riddle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnsweredRiddleRepository extends CrudRepository<AnsweredRiddle, AnswerRiddleKey> {
    List<AnsweredRiddle> findAll();

    List<AnsweredRiddle> findByAnsweredRiddleIdPlayer(Player player);

    List<AnsweredRiddle> findByAnsweredRiddleIdRiddle(Riddle riddle);


}
