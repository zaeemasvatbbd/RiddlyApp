package com.riddly.riddlyapp.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class AnsweredRiddle {

    @EmbeddedId
    private AnswerRiddleKey answeredRiddleId;

}
