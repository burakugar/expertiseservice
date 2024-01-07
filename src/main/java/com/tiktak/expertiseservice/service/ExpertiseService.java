package com.tiktak.expertiseservice.service;

import com.tiktak.expertiseservice.exception.CarNotFoundException;
import com.tiktak.expertiseservice.exception.QuestionNotFoundException;
import com.tiktak.expertiseservice.model.dto.AnswerDTO;
import com.tiktak.expertiseservice.model.dto.ExpertiseResponseDTO;
import com.tiktak.expertiseservice.model.dto.QuestionDTO;
import com.tiktak.expertiseservice.model.entity.Car;
import com.tiktak.expertiseservice.model.entity.ExpertiseResponse;
import com.tiktak.expertiseservice.model.entity.ExpertiseSession;
import com.tiktak.expertiseservice.model.entity.Question;
import com.tiktak.expertiseservice.repository.CarRepository;
import com.tiktak.expertiseservice.repository.ExpertiseResponseRepository;
import com.tiktak.expertiseservice.repository.ExpertiseSessionRepository;
import com.tiktak.expertiseservice.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpertiseService implements ExpertiseServiceImpl {

    private CarRepository carRepository;
    private QuestionRepository questionRepository;
    private ExpertiseResponseRepository expertiseResponseRepository;
    private ExpertiseSessionRepository expertiseSessionRepository;

    public List<QuestionDTO> getQuestionsForCar(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car not found"));

        List<ExpertiseSession> expertiseSessions = expertiseSessionRepository.findByCar(car);
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        List<Question> questions = questionRepository.findAll();

        // Fetch all responses for the sessions in one go
        List<ExpertiseResponse> allResponses = expertiseResponseRepository.findBySessionIn(expertiseSessions);

        for (Question question : questions) {
            QuestionDTO dto = new QuestionDTO();
            dto.setQuestionId(question.getId());
            dto.setContent(question.getContent());

            // Check responses in all sessions and include photo URL if the answer is 'Yes'
            for (ExpertiseResponse response : allResponses) {
                if (response.getQuestion().equals(question) && "Yes".equals(response.getAnswer())) {
                    dto.setPhotoUrl(response.getPhotoUrl());
                    break;
                }
            }

            questionDTOs.add(dto);
        }

        return questionDTOs;
    }

    // Save expertise response
    public void saveExpertiseResponse(ExpertiseResponseDTO responseDTO) {
        // Validate the response DTO
        // Check if the car and session are valid
        Car car = carRepository.findById(responseDTO.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Car not found"));

        ExpertiseSession session = new ExpertiseSession(); // Create or find the session
        session.setCar(car);
        for (AnswerDTO answer : responseDTO.getAnswers()) {
            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new QuestionNotFoundException("Question not found"));

            ExpertiseResponse response = new ExpertiseResponse();
            response.setSession(session);
            response.setQuestion(question);
            response.setAnswer(answer.getAnswer());
            response.setPhotoUrl(answer.getPhotoUrl());
            expertiseResponseRepository.save(response);
        }

    }

}

