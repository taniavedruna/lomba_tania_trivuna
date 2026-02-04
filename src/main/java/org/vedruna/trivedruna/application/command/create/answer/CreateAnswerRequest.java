package org.vedruna.trivedruna.application.command.create.answer;

import org.vedruna.trivedruna.application.cqrs.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAnswerRequest implements Request<CreateAnswerResponse>{

    String selectedAnswer;
    Integer userId;
    Integer questionsId;

}
