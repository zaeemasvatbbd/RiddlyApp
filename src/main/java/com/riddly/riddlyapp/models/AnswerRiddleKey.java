package com.riddly.riddlyapp.models;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class AnswerRiddleKey implements Serializable {

    @ManyToOne()
    @JoinColumn(name = "username", nullable = false)
    private Player player;

    @ManyToOne()
    @JoinColumn(name = "riddleID", nullable = false)
    private Riddle riddle;

}
