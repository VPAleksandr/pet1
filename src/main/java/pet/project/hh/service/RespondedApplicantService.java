package pet.project.hh.service;

import pet.project.hh.Dto.RespondedApplicantDto;
import pet.project.hh.Dto.ResumeDto;
import pet.project.hh.Dto.VacancyDto;

import java.util.List;

public interface RespondedApplicantService {
    void respond(ResumeDto resumeDto, VacancyDto vacancyDto);

    List<RespondedApplicantDto> getResponsesByVacancyId(Long vacancyId);

    void updateResponseStatus(Long responseId, boolean confirmation);
}
