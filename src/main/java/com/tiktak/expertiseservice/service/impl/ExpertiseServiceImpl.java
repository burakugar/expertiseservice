package com.tiktak.expertiseservice.service.impl;

import com.tiktak.expertiseservice.exception.CarNotFoundException;
import com.tiktak.expertiseservice.exception.QuestionNotFoundException;
import com.tiktak.expertiseservice.model.dto.ExpertiseResponseDTO;
import com.tiktak.expertiseservice.model.dto.QuestionDTO;
import com.tiktak.expertiseservice.model.dto.QuestionResponseDTO;
import com.tiktak.expertiseservice.model.entity.Car;
import com.tiktak.expertiseservice.model.entity.ExpertiseResponse;
import com.tiktak.expertiseservice.model.entity.ExpertiseSession;
import com.tiktak.expertiseservice.repository.CarRepository;
import com.tiktak.expertiseservice.repository.ExpertiseResponseRepository;
import com.tiktak.expertiseservice.repository.ExpertiseSessionRepository;
import com.tiktak.expertiseservice.repository.QuestionRepository;
import com.tiktak.expertiseservice.service.ExpertiseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpertiseServiceImpl implements ExpertiseService {

    private final CarRepository carRepository;
    private final QuestionRepository questionRepository;
    private final ExpertiseResponseRepository expertiseResponseRepository;
    private final ExpertiseSessionRepository expertiseSessionRepository;

    public ExpertiseServiceImpl(CarRepository carRepository, QuestionRepository questionRepository,
                                ExpertiseResponseRepository expertiseResponseRepository,
                                ExpertiseSessionRepository expertiseSessionRepository) {
        this.carRepository = carRepository;
        this.questionRepository = questionRepository;
        this.expertiseResponseRepository = expertiseResponseRepository;
        this.expertiseSessionRepository = expertiseSessionRepository;
    }

    public List<QuestionDTO> getQuestionsForCar(Long carId) {
        List<ExpertiseSession> expertiseSessions = expertiseSessionRepository.findByCar_Id(carId);

        if (expertiseSessions.isEmpty()) {
            throw new CarNotFoundException("Car not found or no sessions available for Car ID: " + carId);
        }

        return new ArrayList<>(expertiseResponseRepository
                .findQuestionsWithResponsesBySessions(expertiseSessions)
                .stream()
                .collect(Collectors.toMap(
                        QuestionResponseDTO::getQuestionId,
                        dto -> new QuestionDTO(dto.getQuestionId(), dto.getContent(), dto.getPhotoUrl()),
                        (existing, replacement) -> existing.getPhotoUrl() == null ? replacement : existing))
                .values());
    }


    // Save expertise response
    public void saveExpertiseResponse(@Valid ExpertiseResponseDTO responseDTO) {
        Car car = carRepository.findById(responseDTO.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Car not found"));
        // Create a new session
        ExpertiseSession session = new ExpertiseSession();
        session.setCar(car);
        // Save the session and get the Id, Set the session ID to the responseDTO, Save the responseDTO
        final List<ExpertiseResponse> expertiseResponses = responseDTO.getAnswers().stream()
                .map(answer -> questionRepository.findById(answer.getQuestionId())
                        .map(question -> ExpertiseResponse.builder()
                                .session(session)
                                .question(question)
                                .answer(answer.getAnswer())
                                .photoUrl(answer.getPhotoUrl())
                                .build())
                        .orElseThrow(() -> new QuestionNotFoundException("Question not found")))
                .toList();

        expertiseResponseRepository.saveAll(expertiseResponses);
    }


}

