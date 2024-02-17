package com.uchqun.server.model.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuizResultInfo {
    private int correct;
    private int percentage;
}
