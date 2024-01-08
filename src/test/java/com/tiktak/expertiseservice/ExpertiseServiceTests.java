package com.tiktak.expertiseservice;

import com.tiktak.expertiseservice.exception.CarNotFoundException;
import com.tiktak.expertiseservice.exception.QuestionNotFoundException;
import com.tiktak.expertiseservice.model.dto.AnswerDTO;
import com.tiktak.expertiseservice.model.dto.ExpertiseResponseDTO;
import com.tiktak.expertiseservice.model.dto.QuestionDTO;
import com.tiktak.expertiseservice.model.dto.QuestionResponseDTO;
import com.tiktak.expertiseservice.model.entity.Car;
import com.tiktak.expertiseservice.model.entity.ExpertiseSession;
import com.tiktak.expertiseservice.model.entity.Question;
import com.tiktak.expertiseservice.repository.CarRepository;
import com.tiktak.expertiseservice.repository.ExpertiseResponseRepository;
import com.tiktak.expertiseservice.repository.ExpertiseSessionRepository;
import com.tiktak.expertiseservice.repository.QuestionRepository;
import com.tiktak.expertiseservice.service.impl.ExpertiseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpertiseServiceTests{

    @Mock
    private CarRepository carRepository;
    @Mock
    private ExpertiseSessionRepository sessionRepository;
    @Mock
    private ExpertiseResponseRepository responseRepository;
    @Mock
    private QuestionRepository questionRepository;
    @InjectMocks
    private ExpertiseServiceImpl service;

    @Test
    void whenGetQuestionsForCar_thenReturnQuestionDTOList() {
        final Long carId = 1L;

        // Create a list of sessions
        List<ExpertiseSession> sessions = new ArrayList<>();
        ExpertiseSession session = new ExpertiseSession();
        session.setId(1L);
        sessions.add(session);

        // Create a list of QuestionResponseDTOs
        List<QuestionResponseDTO> questionResponseDTOs = new ArrayList<>();
        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO(1L, "Question content", "photoUrl");
        questionResponseDTOs.add(questionResponseDTO);

        when(sessionRepository.findByCar_Id(carId)).thenReturn(sessions);
        when(responseRepository.findQuestionsWithResponsesBySessions(sessions)).thenReturn(questionResponseDTOs);

        List<QuestionDTO> result = service.getQuestionsForCar(carId);

        assertNotNull(result);
        // Additional assertions to check the correctness of the result
        assertEquals(1, result.size());
        QuestionDTO questionDTO = result.get(0);
        assertEquals(questionResponseDTO.getQuestionId(), questionDTO.getQuestionId());
        assertEquals(questionResponseDTO.getContent(), questionDTO.getContent());
        assertEquals(questionResponseDTO.getPhotoUrl(), questionDTO.getPhotoUrl());
    }

    @Test
    void whenSaveExpertiseResponse_thenSaveResponses() {
        final Long carId = 1L;
        Car car = new Car(carId);

        final Long questionId = 2L;
        Question question = new Question();
        question.setId(questionId);

        ExpertiseResponseDTO responseDTO = new ExpertiseResponseDTO();
        responseDTO.setCarId(carId);

        List<AnswerDTO> answers = new ArrayList<>();
        answers.add(new AnswerDTO(questionId, "Yes", "http://example.com/photo1.jpg"));
        answers.add(new AnswerDTO(questionId, "No", "http://example.com/photo2.jpg"));
        responseDTO.setAnswers(answers);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        doAnswer(invocation -> null).when(responseRepository).saveAll(anyList());

        service.saveExpertiseResponse(responseDTO);

        verify(responseRepository).saveAll(anyList());
    }

    @Test
    void whenNoExpertiseSessions_thenThrowCarNotFoundException() {
        Long carId = 1L;
        when(sessionRepository.findByCar_Id(carId)).thenReturn(Collections.emptyList());

        assertThrows(CarNotFoundException.class, () -> service.getQuestionsForCar(carId));
    }

    @Test
    void whenQuestionNotFoundInSaveResponse_thenThrowException() {
        ExpertiseResponseDTO dto = new ExpertiseResponseDTO();
        dto.setCarId(1L);
        AnswerDTO answer = new AnswerDTO(2L, "Yes", "http://example.com/photo1.jpg");
        dto.setAnswers(Collections.singletonList(answer));

        when(carRepository.findById(dto.getCarId())).thenReturn(Optional.of(new Car()));
        when(questionRepository.findById(answer.getQuestionId())).thenReturn(Optional.empty());

        assertThrows(QuestionNotFoundException.class, () -> service.saveExpertiseResponse(dto));
    }


}
