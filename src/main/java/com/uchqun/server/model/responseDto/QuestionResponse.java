package com.uchqun.server.model.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionResponse {
    private Long id;
    private String pictureUrl;
    private String question;
    private List<OptionResponse> options = new ArrayList<>();// <TestVariant>
}
