package com.tiktak.expertiseservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class CarDTO {
    @NotNull(message = "Car ID is required")
    private Long carId;
}
