package pet.project.hh.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VACANCIES")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    private String name;

    @Lob
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    private double salary;
    private int experienceMin;
    private int experienceMax;
    private boolean isActive;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vacancy", cascade = CascadeType.ALL)
    private List<RespondedApplicant> respondedApplicants;
}
