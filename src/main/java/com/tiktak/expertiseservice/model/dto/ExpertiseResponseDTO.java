package com.tiktak.expertiseservice.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpertiseResponseDTO {
    @NotNull(message = "Session ID must not be null")
    private Long sessionId;
    @NotNull(message = "Car ID must not be null")
    Long carId;
    @NotEmpty(message = "Answers list cannot be empty")
    private List<@Valid AnswerDTO> answers;
}
    