package pet.project.hh.service.impl;

import pet.project.hh.Dto.UserDto;
import pet.project.hh.Dto.VacancyDto;
import pet.project.hh.exceptions.AccessDeniedException;
import pet.project.hh.exceptions.VacancyNotFoundException;
import pet.project.hh.models.Vacancy;
import pet.project.hh.repository.VacancyRepository;
import pet.project.hh.service.CategoryService;
import pet.project.hh.service.UserService;
import pet.project.hh.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final UserService userService;
    private final CategoryService categoryService;
    private final VacancyRepository vacancyRepository;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public List<VacancyDto> getVacanciesByUser(Long authorId) {
        return vacancyRepository.findByAuthor_Id(authorId)
                .stream()
                .map(v -> VacancyDto.builder()
                        .id(v.getId())
                        .name(v.getName())
                        .description(v.getDescription())
                        .authorId(authorId)
                        .categoryId(v.getCategory().getId())
                        .experienceMax(v.getExperienceMax())
                        .experienceMin(v.getExperienceMin())
                        .category(v.getCategory().getName())
                        .salary(v.getSalary())
                        .updatedTime(v.getUpdatedTime())
                        .updatedTimeString(dateFormat.format(v.getUpdatedTime()))
                        .build())
                .toList();
    }

    @Override
    public List<VacancyDto> getAllVacancies() {
        return vacancyRepository.findAll()
                .stream()
                .map(v -> VacancyDto.builder()
                        .id(v.getId())
                        .name(v.getName())
                        .description(v.getDescription())
                        .category(v.getCategory().getName())
                        .authorName(v.getAuthor().getName())
                        .experienceMax(v.getExperienceMax())
                        .experienceMin(v.getExperienceMin())
                        .updatedTimeString(dateFormat.format(v.getUpdatedTime()))
                        .salary(v.getSalary())
                        .build())
                .toList();
    }

    @Override
    public List<VacancyDto> getAllActiveVacancies() {
        return vacancyRepository.findAll()
                .stream()
                .map(v -> VacancyDto.builder()
                        .id(v.getId())
                        .name(v.getName())
                        .description(v.getDescription())
                        .category(v.getCategory().getName())
                        .authorName(v.getAuthor().getName())
                        .experienceMax(v.getExperienceMax())
                        .experienceMin(v.getExperienceMin())
                        .updatedTimeString(dateFormat.format(v.getUpdatedTime()))
                        .salary(v.getSalary())
                        .build())
                .toList();
    }

    @Override
    public Page getAllActiveVacancies(int page, String sortBy) {
        Sort sort = Sort.unsorted();
        if (sortBy != null) {
            switch (sortBy) {
                case "dateDesc":
                    sort = Sort.by(Sort.Direction.DESC, "updatedTime");
                    break;
                case "dateAsc":
                    sort = Sort.by(Sort.Direction.ASC, "updatedTime");
                    break;
                case "salaryDesc":
                    sort = Sort.by(Sort.Direction.DESC, "salary");
                    break;
                case "salaryAsc":
                    sort = Sort.by(Sort.Direction.ASC, "salary");
                    break;
                case "expMinDesc":
                    sort = Sort.by(Sort.Direction.DESC, "experienceMin");
                    break;
                case "expMinAsc":
                    sort = Sort.by(Sort.Direction.ASC, "experienceMin");
                    break;
            }
        }
        Pageable pageable = PageRequest.of(page, 10, sort);
        return vacancyRepository.findAllActiveVacancies(pageable)
                .map(v -> VacancyDto.builder()
                        .authorId(v.getAuthor().getId())
                        .name(v.getName())
                        .description(v.getDescription())
                        .category(v.getCategory().getName())
                        .authorName(v.getAuthor().getName())
                        .id(v.getId())
                        .experienceMax(v.getExperienceMax())
                        .experienceMin(v.getExperienceMin())
                        .updatedTimeString(dateFormat.format(v.getUpdatedTime()))
                        .salary(v.getSalary())
                        .build());
    }

//    @Override
//    public List<VacancyDto> getVacanciesWithApplicantId(Long applicantId) {
//        List<Vacancy> list = vacancyDao.getVacanciesWithApplicant(applicantId);
////        return getListDto(list);
//    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(Long categoryId) {
        return vacancyRepository.findAllByCategoryId(categoryId)
                .stream()
                .map(v -> VacancyDto.builder()
                        .id(v.getId())
                        .name(v.getName())
                        .description(v.getDescription())
                        .category(v.getCategory().getName())
                        .authorName(v.getAuthor().getName())
                        .experienceMax(v.getExperienceMax())
                        .experienceMin(v.getExperienceMin())
                        .updatedTimeString(dateFormat.format(v.getUpdatedTime()))
                        .salary(v.getSalary())
                        .build())
                .toList();
    }

    @Override
    public void createVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setActive(true);
        vacancy.setExperienceMax(vacancyDto.getExperienceMax());
        vacancy.setExperienceMin(vacancyDto.getExperienceMin());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setCreatedTime(LocalDateTime.now());
        vacancy.setUpdatedTime(LocalDateTime.now());
        vacancy.setAuthor(userService.getUserById(vacancyDto.getAuthorId()));
        vacancy.setCategory(categoryService.getCategoryById(vacancyDto.getCategoryId()));
        vacancyRepository.save(vacancy);
    }

    @Override
    public void deleteVacancyById(Long vacancyId) {
        vacancyRepository.deleteById(vacancyId);
    }

    @Override
    public void updateVacancy(Long id, VacancyDto vacancyDto) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(VacancyNotFoundException::new);

        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExperienceMin(vacancyDto.getExperienceMin());
        vacancy.setExperienceMax(vacancyDto.getExperienceMax());
        vacancy.setActive(true);
        vacancy.setCategory(categoryService.getCategoryById(vacancyDto.getCategoryId()));
        vacancy.setUpdatedTime(LocalDateTime.now());

        vacancyRepository.save(vacancy);
    }

    @Override
    public VacancyDto getVacancyById(Long id) {
        Vacancy v = vacancyRepository.findById(id).orElseThrow(VacancyNotFoundException::new);
        return VacancyDto.builder()
                .id(v.getId())
                .name(v.getName())
                .description(v.getDescription())
                .categoryId(v.getCategory().getId())
                .salary(v.getSalary())
                .experienceMax(v.getExperienceMax())
                .experienceMin(v.getExperienceMin())
                .isActive(v.isActive())
                .createdTime(v.getCreatedTime())
                .updatedTime(v.getUpdatedTime())
                .authorName(v.getAuthor().getName())
                .authorId(v.getAuthor().getId())
                .category(v.getCategory().getName())
                .updatedTimeString(dateFormat.format(v.getUpdatedTime()))
                .build();
    }

    @SneakyThrows
    @Override
    public void refreshVacancy(UserDto user, VacancyDto vacancyDto) {
        if (!vacancyDto.getAuthorId().equals(user.getId())) {
            throw new AccessDeniedException();
        }
        Vacancy vacancy = vacancyRepository.findById(vacancyDto.getId()).orElseThrow(VacancyNotFoundException::new);
        vacancy.setUpdatedTime(LocalDateTime.now());
        vacancyRepository.save(vacancy);
    }

    @SneakyThrows
    @Override
    public Vacancy getVacancyModelById(Long id) {
        return vacancyRepository.findById(id).orElseThrow(VacancyNotFoundException::new);
    }
}
