package pet.project.hh.controller.mvc;

import pet.project.hh.Dto.UserDto;
import pet.project.hh.Dto.VacancyDto;
import pet.project.hh.service.CategoryService;
import pet.project.hh.service.ResumeService;
import pet.project.hh.service.UserService;
import pet.project.hh.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyControllerMVC {

    private final VacancyService vacancyService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ResumeService resumeService;

    @GetMapping
    public String vacancies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "dateDesc") String sortBy,
            Model model
    ) {
        Page<VacancyDto> vacancyPage = vacancyService.getAllActiveVacancies(page, sortBy);
        model.addAttribute("vacancies", vacancyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vacancyPage.getTotalPages());
        model.addAttribute("totalItems", vacancyPage.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        return "vacancy/vacancies";
    }

    @GetMapping("create")
    public String createVacancy(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long authorId = userService.getUserByEmail(username).getId();
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setAuthorId(authorId);
        vacancyDto.setActive(true);
        model.addAttribute("vacancyDto", vacancyDto);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "vacancy/create_vacancy";
    }

    @PostMapping
    public String createVacancy(
            @ModelAttribute @Valid VacancyDto vacancyDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (!bindingResult.hasErrors()) {
            vacancyService.createVacancy(vacancyDto);
            return "redirect:/users/profile";
        }
        model.addAttribute("vacancyDto", vacancyDto);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("errors", bindingResult.getAllErrors());
        return "vacancy/create_vacancy";
    }

    @GetMapping("{id}/edit")
    public String editVacancy(@PathVariable Long id, Model model) {
        VacancyDto vacancyDto = vacancyService.getVacancyById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = userService.getUserByEmail(auth.getName()).getId();
        if (!vacancyDto.getAuthorId().equals(userId)) {
            return "redirect:/access-denied";
        }
        model.addAttribute("vacancyDto", vacancyDto);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "vacancy/edit_vacancy";
    }

    @PostMapping("{id}")
    public String updateVacancy(
            @PathVariable Long id,
            @ModelAttribute @Valid VacancyDto vacancyDto,
            BindingResult bindingResult,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = userService.getUserByEmail(auth.getName()).getId();
        if (!vacancyDto.getAuthorId().equals(userId)) {
            return "redirect:/access-denied";
        }
        if (!bindingResult.hasErrors()) {
            vacancyService.updateVacancy(id, vacancyDto);
            return "redirect:/users/profile";
        }
        model.addAttribute("vacancyDto", vacancyDto);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("errors", bindingResult.getAllErrors());
        return "vacancy/edit_vacancy";
    }

    @PostMapping("refresh")
    public String refreshVacancy(
            @RequestParam Long vacancyId,
            @RequestParam String redirectURI,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUserByEmail(auth.getName());
        VacancyDto vac = vacancyService.getVacancyById(vacancyId);
        vacancyService.refreshVacancy(user, vac);
        return "redirect:" + redirectURI;
    }

    @GetMapping("{id}")
    public String getVacancyById(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().contains("anonymous")) {
            UserDto user = userService.getUserByEmail(auth.getName());
            model.addAttribute("user", user);
            model.addAttribute("resumeListForUser", resumeService.getAllResumeByUser(user.getId()));
        }
        VacancyDto vac = vacancyService.getVacancyById(id);
        model.addAttribute("vacancyDto", vac);
        return "vacancy/vacancy";
    }
}
