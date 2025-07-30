package pet.project.hh.repository;

import pet.project.hh.models.RespondedApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespondedApplicantRepository extends JpaRepository<RespondedApplicant, Long> {
    boolean existsByResumeIdAndVacancyId(Long id, Long id1);

    List<RespondedApplicant> findByVacancyId(Long vacancyId);
}
