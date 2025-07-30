package pet.project.hh.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryId", cascade = CascadeType.ALL)
    private List<Category> categories;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    List<Resume> resumes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    List<Vacancy> vacancies;
}
