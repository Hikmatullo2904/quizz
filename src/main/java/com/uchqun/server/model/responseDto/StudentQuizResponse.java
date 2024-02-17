package com.uchqun.server.model.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentQuizResponse extends QuizResponse {
    private String teacherName;
    private QuizResultInfo quizResultInfo;
}
