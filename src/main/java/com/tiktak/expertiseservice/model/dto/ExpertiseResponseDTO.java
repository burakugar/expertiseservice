package com.tiktak.expertiseservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ExpertiseResponseDTO {
    @NotNull(message = "Session ID is required")
    private Long sessionId;
    @NotNull(message = "Car ID is required")
    private Long carId;
    @NotEmpty(message = "Answers are required")
    private List<AnswerDTO> answers;
}
