package com.tiktak.expertiseservice.service;

import com.tiktak.expertiseservice.model.dto.ExpertiseResponseDTO;
import com.tiktak.expertiseservice.model.dto.QuestionDTO;

import java.util.List;

public interface ExpertiseServiceImpl {
    void saveExpertiseResponse(final ExpertiseResponseDTO responseDTO);

    List<QuestionDTO> getQuestionsForCar(final Long carId);
}
