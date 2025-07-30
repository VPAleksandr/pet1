package pet.project.hh.repository;

import pet.project.hh.models.Contact;
import pet.project.hh.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByResumeId(Long resumeId);

    void deleteByResume_Id(Long resumeId);

    void deleteByResume(Resume resume);
}
