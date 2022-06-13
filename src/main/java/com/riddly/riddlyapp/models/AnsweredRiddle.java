package com.riddly.riddlyapp.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class AnsweredRiddle {

    @Embeddable
    static class AnswerRiddleKey implements Serializable {

        @ManyToOne()
        @JoinColumn(name = "username", nullable = false)
        private Player player;

        @ManyToOne()
        @JoinColumn(name = "riddleID", nullable = false)
        private Riddle riddle;

    }

    @EmbeddedId
    private AnswerRiddleKey answeredRiddleId;

}
