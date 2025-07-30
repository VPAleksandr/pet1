package pet.project.hh.controller.mvc;

import home.work.hh.Dto.*;
import pet.project.hh.Dto.*;
import pet.project.hh.exceptions.AccessDeniedException;
import pet.project.hh.service.ImageService;
import pet.project.hh.service.ResumeService;
import pet.project.hh.service.UserService;
import pet.project.hh.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserControllerMVC {

    private final ResumeService resumeService;
    private final UserService userService;
    private final VacancyService vacancyService;
    private final ImageService imageService;

    @GetMapping("profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.getUserByEmail(auth.getName());
        List<ResumeDto> resumeDtoList = resumeService.getAllResumeByUser(userDto.getId());
        List<VacancyDto> vacancyDtoList = vacancyService.getVacanciesByUser(userDto.getId());
        model.addAttribute("resumeDtoList", resumeDtoList);
        model.addAttribute("vacancyDtoList", vacancyDtoList);
        model.addAttribute("userDto", userDto);
        return "user/profile";
    }

    @GetMapping("profile/redact/{id}")
    public String redact(UserDto userDto, @PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userDto", userService.getUserByEmail(auth.getName()));
        return "user/redact";
    }

    @PostMapping("profile/redact/{id}")
    public String redact(
            @ModelAttribute @Valid UserRedactDto userDto,
            @PathVariable Long id,
            BindingResult bindingResult,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUserByEmail(auth.getName());
        model.addAttribute("userDto", user);
        UserRedactDto usr = new UserRedactDto(userDto.getId(), userDto.getName(), userDto.getSurname(), userDto.getAge());
        if (!bindingResult.hasErrors()) {
            userService.updateUser(usr);
        }

        return "user/redact";
    }

    @GetMapping("employers")
    public String getAllEmployers(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<UserDto> employers = userService.getEmployers();
        model.addAttribute("employers", employers);
        return "user/employers";
    }

    @SneakyThrows
    @GetMapping("employer/{id}")
    public String employerProfile(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto currentUser = userService.getUserByEmail(auth.getName());
        UserDto targetUser = userService.getUserDtoById(id);

        if ((currentUser.getAccountType() == 1 && targetUser.getAccountType() != 2)) {
            throw new AccessDeniedException();
        }

        List<VacancyDto> vacancyDtoList = vacancyService.getVacanciesByUser(id);
        model.addAttribute("vacancyDtoList", vacancyDtoList);
        model.addAttribute("userDto", targetUser);
        return "user/profile_guest";
    }

    @SneakyThrows
    @PostMapping("profile/upload-avatar")
    public String uploadAvatar(@ModelAttribute UserAvatarDto userAvatarDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto currentUser = userService.getUserByEmail(auth.getName());

        if (!currentUser.getId().equals(userAvatarDto.getUserId())) {
            throw new AccessDeniedException("Вы не можете загружать аватар для другого пользователя");
        }

        String filename = imageService.uploadAvatar(userAvatarDto.getFile());

        userService.updateUserAvatar(currentUser.getId(), filename);

        return "redirect:/users/profile";
    }

    @GetMapping("avatar/{filename}")
    public ResponseEntity<?> getAvatar(@PathVariable String filename) {
        return imageService.getAvatar(filename);
    }

}
