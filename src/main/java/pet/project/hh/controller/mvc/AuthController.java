package pet.project.hh.controller.mvc;

import pet.project.hh.Dto.UserDto;
import pet.project.hh.models.User;
import pet.project.hh.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("register")
    public String register() {
        return "auth/register";
    }

    @GetMapping("welcome")
    public String welcome() {
        return "auth/welcome";
    }

    @GetMapping("register/applicant")
    public String registerApplicant(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/register_applicant";
    }

    @PostMapping("register/applicant")
    public String registerApplicant(
            @ModelAttribute @Valid UserDto userDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (!bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDto);
            userService.registerApplicant(userDto);
            return "redirect:/auth/login";
        }
        model.addAttribute("userDto", userDto);
        model.addAttribute("errors", bindingResult.getAllErrors());
        return "auth/register_applicant";
    }

    @GetMapping("register/employer")
    public String registerEmployer(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/register_employer";
    }

    @PostMapping("register/employer")
    public String registerEmployer(
            @ModelAttribute @Valid UserDto userDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (!bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDto);
            userService.registerEmployer(userDto);
            return "redirect:/auth/login";
        }
        model.addAttribute("userDto", userDto);
        model.addAttribute("errors", bindingResult.getAllErrors());
        return "/auth/register_employer";
    }

    @GetMapping("forgot_password")
    public String showForgotPasswordForm() {
        return "/auth/forgot_password_form";
    }

    @PostMapping("forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        try {
            userService.makeResetPasswdLink(request);
            model.addAttribute("message", "Мы отправили ссылку для сброса пароля на Вашу почту");
        } catch (UsernameNotFoundException | UnsupportedEncodingException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (MessagingException ex) {
            model.addAttribute("error", "Отправка не удалась");
        }
        return "/auth/forgot_password_form";
    }

    @GetMapping("reset_password")
    public String showResetPasswordForm(
            @RequestParam String token,
            Model model
    ) {
        try {
            userService.getByResetPasswordToken(token);
            model.addAttribute("token", token);
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", "Неверный токен");
        }
        return "/auth/reset_password_form";
    }

    @PostMapping("reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        try {
            User user = userService.getByResetPasswordToken(token);
            userService.updatePassword(user, password);
            model.addAttribute("message", "Успешная смена пароля");
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("message", "Неверный токен");
        }
        return "/auth/message";
    }
}
