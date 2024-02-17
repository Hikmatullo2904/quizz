package com.uchqun.server.model.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OptionResponse {
    private Long id;
    private String label;
    private Boolean isCorrect;
}
