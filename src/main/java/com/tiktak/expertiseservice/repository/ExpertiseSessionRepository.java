package com.tiktak.expertiseservice.repository;

import com.tiktak.expertiseservice.model.entity.Car;
import com.tiktak.expertiseservice.model.entity.ExpertiseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertiseSessionRepository extends JpaRepository<ExpertiseSession, Long> {
    List<ExpertiseSession> findByCar(Car car);
}

