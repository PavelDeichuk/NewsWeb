package com.pavel.newsweb.Factories;

import com.pavel.newsweb.Model.Answer;

public class AnswerFactories {
    public static final Answer answer(boolean answers){
        return Answer
                .builder()
                .answer(true)
                .build();
    }
}
