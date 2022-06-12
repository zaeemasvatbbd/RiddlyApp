package com.riddly.riddlyapp.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import java.io.Serializable;

@Embeddable
public class MyKey implements Serializable {

    @Column(name = "username", nullable = false)
    private Player username;

    @Column(name = "id", nullable = false)
    private Riddle riddleID;

}
