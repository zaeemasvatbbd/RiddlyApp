package com.riddly.riddlyapp.models;

import lombok.*;

import javax.persistence.*;
@Data
@Entity
public class AnswerRiddle {
    @EmbeddedId
    private MyKey myKey;

    @Column(name = "PlayerRiddle")
    private String PlayerRiddle;

}
