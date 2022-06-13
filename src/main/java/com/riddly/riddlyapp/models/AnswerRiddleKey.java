package com.riddly.riddlyapp.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
//@Data
public class AnswerRiddleKey implements Serializable {

    @OneToOne()
    @JoinColumn(name = "username", nullable = false)
    private Player player;

    @OneToOne()
    @JoinColumn(name = "riddleID", nullable = false)
    private Riddle riddle;

}
