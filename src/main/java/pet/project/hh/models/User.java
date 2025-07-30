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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatar;
    private Boolean enabled;
    private String resetPasswordToken;
    private String locale;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ACCOUNT_TYPE")
    private AccountType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resume> resumes;
}
