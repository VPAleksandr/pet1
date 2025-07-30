package pet.project.hh.service.impl;

import pet.project.hh.Dto.RespondedApplicantDto;
import pet.project.hh.Dto.ResumeDto;
import pet.project.hh.Dto.VacancyDto;
import pet.project.hh.models.RespondedApplicant;
import pet.project.hh.models.Resume;
import pet.project.hh.models.Vacancy;
import pet.project.hh.repository.RespondedApplicantRepository;
import pet.project.hh.service.RespondedApplicantService;
import pet.project.hh.service.ResumeService;
import pet.project.hh.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RespondedApplicantServiceImpl implements RespondedApplicantService {

    private final RespondedApplicantRepository applicantRepository;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;

    @Override
    public void respond(ResumeDto resumeDto, VacancyDto vacancyDto) {
        Resume resume = resumeService.getResumeModelById(resumeDto.getId());
        Vacancy vacancy = vacancyService.getVacancyModelById(vacancyDto.getId());

        if (applicantRepository.existsByResumeIdAndVacancyId(resume.getId(), vacancy.getId())) {
            throw new IllegalStateException("You have already responded to this vacancy with this resume");
        }

        RespondedApplicant applicant = new RespondedApplicant();
        applicant.setConfirmation(false);
        applicant.setVacancy(vacancy);
        applicant.setResume(resume);
        applicantRepository.save(applicant);
    }

    @Override
    public List<RespondedApplicantDto> getResponsesByVacancyId(Long vacancyId) {
        return applicantRepository.findByVacancyId(vacancyId)
                .stream()
                .map(response -> RespondedApplicantDto.builder()
                        .id(response.getId())
                        .resumeId(response.getResume().getId())
                        .resumeName(response.getResume().getName())
                        .applicantName(response.getResume().getApplicant().getName())
                        .categoryName(response.getResume().getCategory().getName())
                        .salary(response.getResume().getSalary())
                        .confirmation(response.isConfirmation())
                        .build())
                .toList();
    }

    @Override
    public void updateResponseStatus(Long responseId, boolean confirmation) {
        RespondedApplicant response = applicantRepository.findById(responseId)
                .orElseThrow(() -> new IllegalArgumentException("Response not found"));
        response.setConfirmation(confirmation);
        applicantRepository.save(response);
    }
}