package com.uchqun.server.model.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionRequest {

    @NotBlank
    private String question;

    private String pictureBase64;

    @NotNull
    private List<OptionRequest> options = new ArrayList<>();
}
