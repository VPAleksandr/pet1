package pet.project.hh.service.impl;

import pet.project.hh.Dto.EducationDto;
import pet.project.hh.models.Education;
import pet.project.hh.models.Resume;
import pet.project.hh.repository.EducationRepository;
import pet.project.hh.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    @Override
    public List<EducationDto> getEducationsForResume(Long resumeId) {
        return educationRepository.findByResume_Id(resumeId)
                .stream()
                .map(e -> EducationDto.builder()
                        .id(e.getId())
                        .resumeId(e.getResume().getId())
                        .degree(e.getDegree())
                        .institution(e.getInstitution())
                        .program(e.getProgram())
                        .endDate(e.getEndDate())
                        .startDate(e.getStartDate())
                        .build())
                .toList();
    }

    @Override
    public void deleteEducationsForResume(Resume resume) {
        educationRepository.deleteByResume(resume);
    }

    @Override
    public void addEducationForResume(EducationDto educationDto, Resume resume) {
        Education education = new Education();
        education.setResume(resume);
        education.setDegree(educationDto.getDegree());
        education.setProgram(educationDto.getProgram());
        education.setStartDate(educationDto.getStartDate());
        education.setEndDate(educationDto.getEndDate());
        education.setInstitution(educationDto.getInstitution());
        educationRepository.save(education);
    }

    @Override
    public void deleteEducationsByResumeId(Long resumeId) {
        educationRepository.deleteByResume_Id(resumeId);
    }
}
