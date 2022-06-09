package com.riddly.riddlyapp.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Riddle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int riddleID;

    @Column(name = "riddle")
    String riddleDescription;

    @Column(name = "answer")
    String riddleAnswer;

}
