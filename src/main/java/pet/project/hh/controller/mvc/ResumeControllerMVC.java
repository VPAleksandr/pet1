package pet.project.hh.controller.mvc;

import pet.project.hh.Dto.ResumeDto;
import pet.project.hh.Dto.UserDto;
import pet.project.hh.service.CategoryService;
import pet.project.hh.service.ContactService;
import pet.project.hh.service.ResumeService;
import pet.project.hh.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeControllerMVC {

    private final ResumeService resumeService;
    private final ContactService contactService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public String resumes(Model model) {
        model.addAttribute("resumes", resumeService.getAllActiveResumes());
        return "resume/resumes";
    }

    @GetMapping("create")
    public String createResume(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long applicantId = userService.getUserByEmail(username).getId();
        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setApplicantId(applicantId);
        resumeDto.setActive(true);
        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("contacts", contactService.getAllContactTypes());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "resume/create_resume";
    }

    @PostMapping()
    public String createResume(
            @ModelAttribute @Valid ResumeDto resumeDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (!bindingResult.hasErrors()) {
            resumeService.createResume(resumeDto);
            return "redirect:/users/profile";
        }
        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("contacts", contactService.getAllContactTypes());
        model.addAttribute("errors", bindingResult.getAllErrors());
        return "resume/create_resume";
    }

    @GetMapping("{id}/edit")
    public String editResume(@PathVariable Long id, Model model) {
        ResumeDto resumeDto = resumeService.findResumeById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = userService.getUserByEmail(auth.getName()).getId();
        if (!resumeDto.getApplicantId().equals(userId)) {
            return "redirect:/access-denied";
        }
        // Загружаем связанные сущности
        resumeDto.setContacts(resumeService.getContactsForResume(id));
        resumeDto.setEducations(resumeService.getEducationsForResume(id));
        resumeDto.setWorkExperiences(resumeService.getWorkExperiencesForResume(id));
        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("contacts", contactService.getAllContactTypes());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "resume/edit_resume";
    }

    @PostMapping("{id}")
    public String updateResume(
            @PathVariable Long id,
            @ModelAttribute @Valid ResumeDto resumeDto,
            BindingResult bindingResult,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = userService.getUserByEmail(auth.getName()).getId();
        if (!resumeDto.getApplicantId().equals(userId)) {
            return "redirect:/access-denied";
        }
        if (resumeDto.getCategoryId() == null && !categoryService.getAllCategories().isEmpty()) {
            resumeDto.setCategoryId(categoryService.getAllCategories().get(0).getId());
        }
        if (!bindingResult.hasErrors()) {
            resumeService.updateResume(id, resumeDto);
            return "redirect:/users/profile";
        }
        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("contacts", contactService.getAllContactTypes());
        model.addAttribute("errors", bindingResult.getAllErrors());
        return "resume/edit_resume";
    }

    @PostMapping("refresh")
    public String refresh(
            @RequestParam Long resumeId,
            @RequestParam String redirectURI,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUserByEmail(auth.getName());
        ResumeDto resumeDto = resumeService.findResumeById(resumeId);
        resumeService.refreshResume(user, resumeDto);
        return "redirect:" + redirectURI;
    }
}
