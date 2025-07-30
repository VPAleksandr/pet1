package pet.project.hh.service;

import home.work.hh.Dto.*;
import pet.project.hh.Dto.*;
import pet.project.hh.models.Resume;
import lombok.SneakyThrows;

import java.util.List;

public interface ResumeService {


    List<ResumeDto> getAllResumes();

    List<ResumeDto> getAllActiveResumes();

    @SneakyThrows
    ResumeDto findResumeById(Long id);

    List<ResumeDto> getAllResumesByCategory(Long categoryId);

    List<ResumeDto> getAllResumeByUser(Long id);

    @SneakyThrows
    void updateResume(Long id, ResumeDto resumeDto);

    @SneakyThrows
    void refreshResume(UserDto user, ResumeDto resumeDto);

    void createResume(ResumeDto resumeDto);

    void deleteResumeById(Long resumeId);

    Resume getResumeModelById(Long resumeId);

    List<ContactDto> getContactsForResume(Long resumeId);

    List<EducationDto> getEducationsForResume(Long resumeId);

    List<WorkExperienceDto> getWorkExperiencesForResume(Long resumeId);
}
