package pet.project.hh.repository;

import pet.project.hh.models.Resume;
import pet.project.hh.models.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {

    void deleteWorkExperiencesByResumeId(Long resumeId);

    List<WorkExperience> findByResume_Id(Long resumeId);

    void deleteByResume(Resume resume);
}
