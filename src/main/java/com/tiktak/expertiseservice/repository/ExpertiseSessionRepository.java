package com.tiktak.expertiseservice.repository;

import com.tiktak.expertiseservice.model.entity.ExpertiseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertiseSessionRepository extends JpaRepository<ExpertiseSession, Long> {
    List<ExpertiseSession> findByCar_Id(Long carId);

}

