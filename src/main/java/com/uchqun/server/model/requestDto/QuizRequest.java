package com.uchqun.server.model.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizRequest {

    @NotBlank
    private String title;

    private String description;

    private Long duration;

    private Boolean isVisible;

    private List<QuestionRequest> questions = new ArrayList<>();

}
