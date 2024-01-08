package com.tiktak.expertiseservice.cli;

import com.tiktak.expertiseservice.model.entity.Car;
import com.tiktak.expertiseservice.model.entity.ExpertiseSession;
import com.tiktak.expertiseservice.model.entity.Question;
import com.tiktak.expertiseservice.repository.CarRepository;
import com.tiktak.expertiseservice.repository.ExpertiseSessionRepository;
import com.tiktak.expertiseservice.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public final class DataInitializer implements CommandLineRunner {

    private QuestionRepository questionRepository;
    private CarRepository carRepository;
    private ExpertiseSessionRepository sessionRepository;

    @Override
    public void run(String... args) {
        if (questionRepository.count() == 0) {
            questionRepository.save(new Question(1L, "Is the vehicle in good mechanical condition?"));
            questionRepository.save(new Question(2L, "Are there any visible signs of damage?"));
            questionRepository.save(new Question(3L, "Does the vehicle require immediate repairs?"));

        }
        // Initialize Cars
        if (carRepository.count() == 0) {
            carRepository.save(new Car(1L));
            carRepository.save(new Car(2L));
        }

        // Initialize Expertise Sessions
        if (sessionRepository.count() == 0) {
            Car car1 = carRepository.findById(1L).orElse(null);
            Car car2 = carRepository.findById(2L).orElse(null);
            if (car1 != null && car2 != null) {
                sessionRepository.save(new ExpertiseSession(1L, car1, LocalDate.now().minusMonths(1).minusDays(2)));
                sessionRepository.save(new ExpertiseSession(2L, car2, LocalDate.now().minusMonths(10).minusDays(1)));
            }
        }
    }

}

