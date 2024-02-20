package com.uchqun.server.model.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuizDetailedResponse extends QuizResponse {
    private List<QuestionResponse> questions = new ArrayList<>();
}
