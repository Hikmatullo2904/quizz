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

    private String title;

    private int testItemsCount;

    private String description;

    private Boolean isVisible;
    private String teacherName;

}
