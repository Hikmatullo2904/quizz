package com.uchqun.server.model.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionResultRequest {
    private Long questionId;
    private Long optionId;
}
