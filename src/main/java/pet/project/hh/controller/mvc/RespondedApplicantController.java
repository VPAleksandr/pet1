package pet.project.hh.controller.mvc;

import pet.project.hh.Dto.RespondedApplicantDto;
import pet.project.hh.Dto.ResumeDto;
import pet.project.hh.Dto.UserDto;
import pet.project.hh.Dto.VacancyDto;
import pet.project.hh.exceptions.AccessDeniedException;
import pet.project.hh.service.RespondedApplicantService;
import pet.project.hh.service.ResumeService;
import pet.project.hh.service.UserService;
import pet.project.hh.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("respond")
@RequiredArgsConstructor
public class RespondedApplicantController {

    private final RespondedApplicantService applicantService;
    private final UserService userService;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;

    @PostMapping
    public String respond(
            @RequestParam Long vacancyId,
            @RequestParam Long resumeId,
            @RequestParam String redirectURI,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUserByEmail(auth.getName());

        if (user.getAccountType() != 1) {
            redirectAttributes.addFlashAttribute("error", "Only applicants can respond to vacancies");
            return "redirect:" + redirectURI;
        }

        VacancyDto vacancy = vacancyService.getVacancyById(vacancyId);
        ResumeDto resume = resumeService.findResumeById(resumeId);

        if (!resume.getApplicantId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("error", "You can only respond with your own resume");
            return "redirect:" + redirectURI;
        }

        try {
            applicantService.respond(resume, vacancy);
            redirectAttributes.addFlashAttribute("success", "Successfully responded to the vacancy");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:" + redirectURI;
    }

    @SneakyThrows
    @GetMapping("vacancy/{vacancyId}")
    public String viewResponses(@PathVariable Long vacancyId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUserByEmail(auth.getName());

        if (user.getAccountType() != 2) {
            throw new AccessDeniedException("Only employers can view responses");
        }

        VacancyDto vacancy = vacancyService.getVacancyById(vacancyId);

        if (!vacancy.getAuthorId().equals(user.getId())) {
            throw new AccessDeniedException("You can only view responses for your own vacancies");
        }

        List<RespondedApplicantDto> responses = applicantService.getResponsesByVacancyId(vacancyId);
        model.addAttribute("vacancyDto", vacancy);
        model.addAttribute("responses", responses);
        return "respond/responded_applicants";
    }

    @PostMapping("confirm")
    public String confirmResponse(
            @RequestParam Long responseId,
            @RequestParam Long vacancyId,
            RedirectAttributes redirectAttributes
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUserByEmail(auth.getName());

        VacancyDto vacancy = vacancyService.getVacancyById(vacancyId);
        if (!vacancy.getAuthorId().equals(user.getId()) || user.getAccountType() != 2) {
            redirectAttributes.addFlashAttribute("error", "You are not authorized to perform this action");
            return "redirect:/respond/vacancy/" + vacancyId;
        }

        applicantService.updateResponseStatus(responseId, true);
        redirectAttributes.addFlashAttribute("success", "Response confirmed successfully");
        return "redirect:/respond/vacancy/" + vacancyId;
    }

    @PostMapping("reject")
    public String rejectResponse(
            @RequestParam Long responseId,
            @RequestParam Long vacancyId,
            RedirectAttributes redirectAttributes
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUserByEmail(auth.getName());

        VacancyDto vacancy = vacancyService.getVacancyById(vacancyId);
        if (!vacancy.getAuthorId().equals(user.getId()) || user.getAccountType() != 2) {
            redirectAttributes.addFlashAttribute("error", "You are not authorized to perform this action");
            return "redirect:/respond/vacancy/" + vacancyId;
        }

        applicantService.updateResponseStatus(responseId, false);
        redirectAttributes.addFlashAttribute("success", "Response rejected successfully");
        return "redirect:/respond/vacancy/" + vacancyId;
    }
}