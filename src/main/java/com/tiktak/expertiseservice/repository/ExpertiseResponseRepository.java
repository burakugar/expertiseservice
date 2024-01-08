package com.tiktak.expertiseservice.repository;

import com.tiktak.expertiseservice.model.dto.QuestionResponseDTO;
import com.tiktak.expertiseservice.model.entity.ExpertiseResponse;
import com.tiktak.expertiseservice.model.entity.ExpertiseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertiseResponseRepository extends JpaRepository<ExpertiseResponse, Long> {
    @Query("SELECT new com.tiktak.expertiseservice.model.dto.QuestionResponseDTO(q.id, q.content, er.photoUrl) " +
            "FROM question q " +
            "LEFT JOIN expertise_response er ON er.question.id = q.id " +
            "AND er.session IN :sessions " +
            "WHERE er.answer = 'Yes' OR er.answer IS NULL")
    List<QuestionResponseDTO> findQuestionsWithResponsesBySessions(@Param("sessions") List<ExpertiseSession> sessions);

}

