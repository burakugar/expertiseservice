package com.tiktak.expertiseservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;


@Getter
@AllArgsConstructor
public final class AnswerDTO {
    @NotNull(message = "Question ID is required")
    private Long questionId;

    @NotBlank(message = "Answer is required")
    @Size(max = 4, message = "Answer name cannot exceed 4 characters")
    private String answer;

    @URL(message = "Invalid URL format for photo")
    private String photoUrl;
}
