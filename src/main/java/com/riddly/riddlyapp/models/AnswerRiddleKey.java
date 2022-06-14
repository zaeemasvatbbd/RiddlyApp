package com.riddly.riddlyapp.models;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
public class AnswerRiddleKey implements Serializable {

    @ManyToOne()
    @JoinColumn(name = "username", nullable = false)
    private Player player;

    @ManyToOne()
    @JoinColumn(name = "riddleID", nullable = false)
    private Riddle riddle;

}