package pet.project.hh.service;

import pet.project.hh.Dto.UserDto;
import pet.project.hh.Dto.VacancyDto;
import pet.project.hh.models.Vacancy;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VacancyService {

    List<VacancyDto> getVacanciesByUser(Long authorId);

    List<VacancyDto> getAllVacancies();

    List<VacancyDto> getAllActiveVacancies();

    Page<VacancyDto> getAllActiveVacancies(int page, String sortBy);

    List<VacancyDto> getVacanciesByCategory(Long category);

    void createVacancy(VacancyDto vacancyDto);

    void deleteVacancyById(Long vacancyId);

    void updateVacancy(Long id, VacancyDto vacancyDto);

    VacancyDto getVacancyById(Long id);

    @SneakyThrows
    void refreshVacancy(UserDto user, VacancyDto vacancyDto);

    Vacancy getVacancyModelById(Long id);
}
