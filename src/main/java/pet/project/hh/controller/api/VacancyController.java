package pet.project.hh.controller.api;

import pet.project.hh.Dto.VacancyDto;
import pet.project.hh.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public List<VacancyDto> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

//    @GetMapping("user/{userId}")
//    public List<VacancyDto> getVacanciesWithApplicantId(@PathVariable Long userId) {
//        return vacancyService.getVacanciesWithApplicantId(userId);
//    }

    @GetMapping("{categoryId}")
    public List<VacancyDto> getVacanciesByCategory(@PathVariable("categoryId") Long categoryId) {
        return vacancyService.getVacanciesByCategory(categoryId);
    }

    @PostMapping("{id}")
    public HttpStatus sendResume(@PathVariable("id") String id) {
        // TODO send resume to vacancy by applicant
        return HttpStatus.OK;
    }

//    @PostMapping
//    public HttpStatus createVacancy(@RequestBody @Valid VacancyDto vacancyDto) {
//        vacancyService.createVacancy(vacancyDto);
//        return HttpStatus.CREATED;
//    }

//    @PutMapping("{id}")
//    public HttpStatus updateVacancy(@PathVariable("id") Long id, @Valid @RequestBody VacancyDto vacancyDto) {
//        vacancyService.updateVacancy(id, vacancyDto);
//        return HttpStatus.OK;
//    }

    @DeleteMapping("{id}")
    public HttpStatus deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteVacancyById(id);
        return HttpStatus.OK;
    }

}
