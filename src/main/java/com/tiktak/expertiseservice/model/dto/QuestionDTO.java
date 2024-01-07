package com.tiktak.expertiseservice.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class QuestionDTO {
    @NotNull(message = "Question ID is required")
    private Long questionId;

    @NotBlank(message = "Content is required")
    private String content;

    @URL(message = "Invalid URL format")
    private String photoUrl;
}
