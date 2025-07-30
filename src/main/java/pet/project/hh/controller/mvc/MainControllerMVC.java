package pet.project.hh.controller.mvc;

import pet.project.hh.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainControllerMVC {

    private final VacancyService vacancyService;

    @GetMapping
    public String vacancies(Model model) {
        model.addAttribute("vacancies", vacancyService.getAllActiveVacancies());
        return "vacancy/vacancies";
    }
}
