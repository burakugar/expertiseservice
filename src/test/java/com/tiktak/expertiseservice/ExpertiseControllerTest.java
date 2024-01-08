package com.tiktak.expertiseservice;

import com.tiktak.expertiseservice.controller.ExpertiseController;
import com.tiktak.expertiseservice.model.dto.ExpertiseResponseDTO;
import com.tiktak.expertiseservice.model.dto.QuestionDTO;
import com.tiktak.expertiseservice.service.impl.ExpertiseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExpertiseControllerTest {

    @Mock
    private ExpertiseServiceImpl expertiseService;

    @InjectMocks
    private ExpertiseController expertiseController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(expertiseController).build();
    }

    @Test
    public void testGetQuestions() throws Exception {
        QuestionDTO questionDTO = new QuestionDTO();
        List<QuestionDTO> questionDTOs = Arrays.asList(questionDTO);

        when(expertiseService.getQuestionsForCar(anyLong())).thenReturn(questionDTOs);

        mockMvc.perform(get("/api/v1/expertise/questions/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(expertiseService, times(1)).getQuestionsForCar(anyLong());
    }

    @Test
    public void testSaveExpertiseResponse() throws Exception {

        doNothing().when(expertiseService).saveExpertiseResponse(any(ExpertiseResponseDTO.class));

        String requestBody = "{"
                + "\"carId\": 2,"
                + "\"sessionId\": 1,"
                + "\"answers\": ["
                + "{"
                + "\"questionId\": 1,"
                + "\"answer\": \"Yes\","
                + "\"photoUrl\": \"http://example.com/photo1.jpg\""
                + "},"
                + "{"
                + "\"questionId\": 2,"
                + "\"answer\": \"No\","
                + "\"photoUrl\": \"http://example.com/photo2.jpg\""
                + "}"
                + "]"
                + "}";

        mockMvc.perform(post("/api/v1/expertise/response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        verify(expertiseService, times(1)).saveExpertiseResponse(any(ExpertiseResponseDTO.class));
    }
}
