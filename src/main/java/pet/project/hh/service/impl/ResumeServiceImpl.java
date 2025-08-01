package pet.project.hh.service.impl;

import pet.project.hh.Dto.*;
import pet.project.hh.exceptions.AccessDeniedException;
import pet.project.hh.exceptions.ResumeNotFoundException;
import pet.project.hh.models.Resume;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pet.project.hh.repository.ResumeRepository;
import pet.project.hh.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final WorkExperienceService workExperienceService;
    private final ContactService contactService;
    private final EducationService educationService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Override
    public List<ResumeDto> getAllResumes() {
        return resumeRepository.findAll()
                .stream()
                .map(r -> ResumeDto.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .salary(r.getSalary())
                        .applicantName(r.getApplicant().getName())
                        .categoryName(r.getCategory().getName())
                        .updatedTimeString(dateFormat.format(r.getUpdatedTime()))
                        .updatedTime(r.getUpdatedTime())
                        .build())
                .toList();
    }

    @Override
    public List<ResumeDto> getAllActiveResumes() {
        return resumeRepository.findAllActiveResumes()
                .stream()
                .map(r -> ResumeDto.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .salary(r.getSalary())
                        .applicantName(r.getApplicant().getName())
                        .categoryName(r.getCategory().getName())
                        .updatedTime(r.getUpdatedTime())
                        .updatedTimeString(dateFormat.format(r.getUpdatedTime()))
                        .build())
                .toList();
    }

    @Override
    @SneakyThrows
    public ResumeDto findResumeById(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(ResumeNotFoundException::new);
        return ResumeDto.builder()
                .id(resume.getId())
                .name(resume.getName())
                .salary(resume.getSalary())
                .categoryId(resume.getCategory().getId())
                .applicantId(resume.getApplicant().getId())
                .applicantName(resume.getApplicant().getName())
                .categoryName(resume.getCategory().getName())
                .updatedTime(resume.getUpdatedTime())
                .updatedTimeString(dateFormat.format(resume.getUpdatedTime()))
                .build();
    }

    @Override
    public List<ResumeDto> getAllResumesByCategory(Long categoryId) {
        return resumeRepository.findByCategory_Id(categoryId)
                .stream()
                .map(r -> ResumeDto.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .salary(r.getSalary())
                        .applicantName(r.getApplicant().getName())
                        .categoryName(r.getCategory().getName())
                        .updatedTime(r.getUpdatedTime())
                        .isActive(r.isActive())
                        .updatedTimeString(dateFormat.format(r.getUpdatedTime()))
                        .build())
                .toList();
    }

    @Override
    public List<ResumeDto> getAllResumeByUser(Long applicantId) {
        List<Resume> list = resumeRepository.findByApplicant_Id(applicantId);
        return list.stream()
                .map(r -> ResumeDto.builder()
                        .id(r.getId())
                        .applicantId(r.getApplicant().getId())
                        .name(r.getName())
                        .salary(r.getSalary())
                        .applicantName(r.getApplicant().getName())
                        .categoryName(r.getCategory().getName())
                        .categoryId(r.getCategory().getId())
                        .updatedTime(r.getUpdatedTime())
                        .isActive(r.isActive())
                        .updatedTimeString(dateFormat.format(r.getUpdatedTime()))
                        .build())
                .toList();
    }

    @SneakyThrows
    @Override
    public void updateResume(Long id, ResumeDto resumeDto) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(ResumeNotFoundException::new);

        resume.setName(resumeDto.getName());
        resume.setSalary(resumeDto.getSalary());
        resume.setCategory(categoryService.getCategoryById(resumeDto.getCategoryId()));
        resume.setActive(true);
        resume.setUpdatedTime(LocalDateTime.now());

        contactService.deleteContactsForResume(resume.getId());
        educationService.deleteEducationsForResume(resume);
        workExperienceService.deleteWorkExperiencesForResume(resume);

        resumeRepository.save(resume);

        setResumeParams(resumeDto, resume);
    }

    @SneakyThrows
    @Override
    public void refreshResume(UserDto user, ResumeDto resumeDto) {
        if (!resumeDto.getApplicantId().equals(user.getId())) {
            throw new AccessDeniedException();
        }
        Resume resume = resumeRepository.findById(resumeDto.getId()).orElseThrow(ResumeNotFoundException::new);
        resume.setUpdatedTime(LocalDateTime.now());
        resumeRepository.save(resume);
    }

    @Override
    public void createResume(ResumeDto resumeDto) {
        Resume resume = Resume.builder()
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .name(resumeDto.getName())
                .applicant(userService.getUserById(resumeDto.getApplicantId()))
                .category(categoryService.getCategoryById(resumeDto.getCategoryId()))
                .salary(resumeDto.getSalary())
                .isActive(true)
                .build();
        resumeRepository.save(resume);
        setResumeParams(resumeDto, resume);
    }

    @Override
    public void deleteResumeById(Long resumeId) {
        resumeRepository.deleteById(resumeId);
    }

    private void setResumeParams(ResumeDto resumeDto, Resume resume) {
        if (!resumeDto.getContacts().isEmpty()) {
            resumeDto.getContacts().forEach(contact ->
                    contactService.saveContactForResume(contact, resume));
        }
        if (!resumeDto.getEducations().isEmpty()) {
            resumeDto.getEducations().forEach(educationDto ->
                    educationService.addEducationForResume(educationDto, resume));
        }
        if (!resumeDto.getWorkExperiences().isEmpty()) {
            resumeDto.getWorkExperiences().forEach(workExperienceDto ->
                    workExperienceService.createWorkExperienceForResume(workExperienceDto, resume));
        }
    }

    @Override
    @SneakyThrows
    public Resume getResumeModelById(Long resumeId) {
        return resumeRepository.findById(resumeId).orElseThrow(ResumeNotFoundException::new);
    }

    @Override
    public List<ContactDto> getContactsForResume(Long resumeId) {
        return contactService.getContactsByResumeId(resumeId);
    }

    @Override
    public List<EducationDto> getEducationsForResume(Long resumeId) {
        return educationService.getEducationsForResume(resumeId);
    }

    @Override
    public List<WorkExperienceDto> getWorkExperiencesForResume(Long resumeId) {
        return workExperienceService.getWorkExperiencesForResume(resumeId);
    }

}
