package com.tiktak.expertiseservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class QuestionResponseDTO {
    private Long questionId;
    private String content;
    private String photoUrl;
}

