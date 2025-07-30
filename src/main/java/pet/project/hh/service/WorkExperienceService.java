package pet.project.hh.service;

import pet.project.hh.Dto.WorkExperienceDto;
import pet.project.hh.models.Resume;

import java.util.List;

public interface WorkExperienceService {
    void deleteWorkExperiencesForResume(Resume resume);

    void createWorkExperienceForResume(WorkExperienceDto workExperienceDto, Resume resume);

    void deleteWorkExperienceById(Long id);

    void deleteWorkExperiencesByResumeId(Long resumeId);

    List<WorkExperienceDto> getWorkExperiencesForResume(Long resumeId);
}
