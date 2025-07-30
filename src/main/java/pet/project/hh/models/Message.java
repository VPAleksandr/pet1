package pet.project.hh.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "RESPONDED_APPLICANT_ID")
    private RespondedApplicant respondedApplicant;

    @Lob
    private String content;
    private LocalDateTime localDateTime;
}
