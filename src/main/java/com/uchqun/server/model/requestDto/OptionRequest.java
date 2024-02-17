package com.uchqun.server.model.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OptionRequest {

    @NotBlank
    private String label;

    private Boolean isCorrect = false;

}
