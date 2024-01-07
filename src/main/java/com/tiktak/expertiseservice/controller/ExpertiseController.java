package com.tiktak.expertiseservice.controller;

import com.tiktak.expertiseservice.exception.CarNotFoundException;
import com.tiktak.expertiseservice.exception.InvalidExpertiseResponseException;
import com.tiktak.expertiseservice.model.dto.ExpertiseResponseDTO;
import com.tiktak.expertiseservice.model.dto.QuestionDTO;
import com.tiktak.expertiseservice.service.ExpertiseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expertise")
@AllArgsConstructor
public class ExpertiseController {

    private ExpertiseService expertiseService;

    @GetMapping("/questions/{carId}")
    public ResponseEntity<List<QuestionDTO>> getQuestions(@PathVariable Long carId) {
        List<QuestionDTO> questions = expertiseService.getQuestionsForCar(carId);
        return ResponseEntity.ok(questions);
    }

    // Endpoint to save expertise response
    @PostMapping("/response")
    public ResponseEntity<String> saveExpertiseResponse(@RequestBody ExpertiseResponseDTO responseDTO) {
        expertiseService.saveExpertiseResponse(responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Response saved successfully");
    }

}

