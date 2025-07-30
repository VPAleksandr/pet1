package pet.project.hh.service.impl;

import pet.project.hh.Dto.WorkExperienceDto;
import pet.project.hh.models.Resume;
import pet.project.hh.models.WorkExperience;
import pet.project.hh.repository.WorkExperienceRepository;
import pet.project.hh.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;

    public List<WorkExperienceDto> getWorkExperiencesForResume(Long resumeId) {
        return workExperienceRepository.findByResume_Id(resumeId)
                .stream()
                .map(w -> WorkExperienceDto.builder()
                        .id(w.getId())
                        .years(w.getYears())
                        .resumeId(resumeId)
                        .companyName(w.getCompanyName())
                        .responsibilities(w.getResponsibilities())
                        .position(w.getPosition())
                        .build())
                .toList();
    }

    @Override
    public void deleteWorkExperiencesForResume(Resume resume) {
        workExperienceRepository.deleteByResume(resume);
    }

    @Override
    public void createWorkExperienceForResume(WorkExperienceDto workExperienceDto, Resume resume) {
        WorkExperience workExperience = new WorkExperience();
        workExperience.setResume(resume);
        workExperience.setCompanyName(workExperienceDto.getCompanyName());
        workExperience.setPosition(workExperienceDto.getPosition());
        workExperience.setResponsibilities(workExperienceDto.getResponsibilities());
        workExperience.setYears(workExperienceDto.getYears());
        workExperienceRepository.save(workExperience);
    }

    @Override
    public void deleteWorkExperienceById(Long id) {
        workExperienceRepository.deleteById(id);
    }

    @Override
    public void deleteWorkExperiencesByResumeId(Long resumeId) {
        workExperienceRepository.deleteWorkExperiencesByResumeId(resumeId);
    }
}
