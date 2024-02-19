package com.uchqun.server.model.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {
    private Long id;

    private String testName;

    private int testItemsCount;

    private String description;

    private Long duration;

    private Boolean isVisible;

}
