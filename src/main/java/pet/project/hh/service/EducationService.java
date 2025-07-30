package pet.project.hh.service;

import pet.project.hh.Dto.EducationDto;
import pet.project.hh.models.Resume;

import java.util.List;

public interface EducationService {

    List<EducationDto> getEducationsForResume(Long resumeId);

    void deleteEducationsForResume(Resume resume);

    void addEducationForResume(EducationDto educationDto, Resume resume);

    void deleteEducationsByResumeId(Long resumeId);
}
