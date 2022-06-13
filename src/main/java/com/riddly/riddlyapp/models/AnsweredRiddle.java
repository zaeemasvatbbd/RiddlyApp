package com.riddly.riddlyapp.models;

import lombok.*;

import javax.persistence.*;
@Data
@Entity
public class AnsweredRiddle {
    @EmbeddedId
    private AnswerRiddleKey answeredRiddleId;

//    @Column(name = "PlayerRiddle")
//    private String PlayerRiddle;

}
