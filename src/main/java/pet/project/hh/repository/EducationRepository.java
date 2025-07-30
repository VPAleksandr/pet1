package pet.project.hh.repository;

import pet.project.hh.models.Education;
import pet.project.hh.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    void deleteByResume_Id(Long resumeId);

    List<Education> findByResume_Id(Long resumeId);

    void deleteByResume(Resume resume);
}
