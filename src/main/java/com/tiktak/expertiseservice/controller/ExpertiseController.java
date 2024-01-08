package com.tiktak.expertiseservice.controller;

import com.tiktak.expertiseservice.model.dto.ExpertiseResponseDTO;
import com.tiktak.expertiseservice.model.dto.QuestionDTO;
import com.tiktak.expertiseservice.service.impl.ExpertiseServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expertise")
@AllArgsConstructor
public class ExpertiseController {

    private ExpertiseServiceImpl expertiseService;

    @GetMapping("/questions/{carId}")
    public ResponseEntity<List<QuestionDTO>> getQuestions(@PathVariable Long carId) {
        return ResponseEntity.ok(expertiseService.getQuestionsForCar(carId));
    }

    @PostMapping("/response")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveExpertiseResponse(@Valid @RequestBody ExpertiseResponseDTO responseDTO) {
        expertiseService.saveExpertiseResponse(responseDTO);
    }

}

