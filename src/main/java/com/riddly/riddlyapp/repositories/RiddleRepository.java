package com.riddly.riddlyapp.repositories;


import com.riddly.riddlyapp.models.Player;
import com.riddly.riddlyapp.models.Riddle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiddleRepository extends CrudRepository<Riddle, Integer>  {
    List<Riddle> findAll();

}
