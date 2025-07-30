package pet.project.hh.repository;

import pet.project.hh.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    @Query("select r from Resume r where r.isActive = true")
    List<Resume> findAllActiveResumes();

    List<Resume> findByCategory_Id(Long categoryId);

    List<Resume> findByApplicant_Id(Long applicantId);


}
