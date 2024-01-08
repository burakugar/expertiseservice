package com.tiktak.expertiseservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity(name = "expertise_response")
public class ExpertiseResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expertise_session_id", nullable = false)
    private ExpertiseSession session;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "answer", nullable = false)
    @Size(max = 3)
    private String answer;

    @Column(name = "photo_url", nullable = false)
    @Size(max = 255)
    private String photoUrl;

}
