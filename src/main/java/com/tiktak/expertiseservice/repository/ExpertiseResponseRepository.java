package com.tiktak.expertiseservice.repository;

import com.tiktak.expertiseservice.model.entity.ExpertiseResponse;
import com.tiktak.expertiseservice.model.entity.ExpertiseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertiseResponseRepository extends JpaRepository<ExpertiseResponse, Long> {

    List<ExpertiseResponse> findBySessionIn(List<ExpertiseSession> sessions);
}

