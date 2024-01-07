package com.tiktak.expertiseservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class AnswerDTO {
    @NotNull(message = "Question ID is required")
    private Long questionId;

    @NotBlank(message = "Answer is required")
    @Size(max = 4, message = "Answer name cannot exceed 4 characters")
    private String answer;

    @URL(message = "Invalid URL format for photo")
    private String photoUrl;
}
