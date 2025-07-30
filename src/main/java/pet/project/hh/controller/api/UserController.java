package pet.project.hh.controller.api;

import pet.project.hh.Dto.UserDto;
import pet.project.hh.dao.UserDao;
import pet.project.hh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDao userDao;

    @GetMapping("firms")
    public List<UserDto> getEmployers() {
        return userService.getEmployers();
    }

    @GetMapping("user/name/{name}")
    public List<UserDto> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("user/number/{number}")
    public UserDto getUserByNumber(@PathVariable String number) {
        return userService.getUserByNumber(number);
    }

    @GetMapping("user/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("is/user/{email}")
    public boolean isUserHear(@PathVariable String email) {
        return userService.isUserHear(email);
    }

//    @PostMapping
//    public HttpStatus registerUser(@RequestBody @Valid UserDto user) {
//        try {
//            userService.registerUser(user);
//            return HttpStatus.CREATED;
//        } catch (IllegalArgumentException e) {
//            return HttpStatus.CONFLICT;
//        }
//    }

    @GetMapping("/current-locale")
    public String getDefaultLocale() {
        Locale defaultLocale = Locale.getDefault();
        return "Default Locale: " + defaultLocale +
                "\nLanguage: " + defaultLocale.getLanguage() +
                "\nCountry: " + defaultLocale.getCountry();
    }

    @PostMapping("profile")
    public HttpStatus login(@RequestBody String userEmail, String userPassword) {
        // TODO login profile
        return HttpStatus.OK;
    }

    @GetMapping("employer/{userName}")
    public HttpStatus getEmployer(@PathVariable("userName") String userName) {
        userDao.getUserByName(userName);
        return HttpStatus.OK;
    }

    @GetMapping("email/{userEmail}")
    public HttpStatus getUser(@PathVariable("userEmail") String userEmail) {
        userDao.getUserByEmail(userEmail);
        return HttpStatus.OK;
    }

//    @GetMapping("/vacancy/{vacancyId}")
//    public List<UserDto> getApplicantsForVacancy(@PathVariable("vacancyId") Long vacancyId) {
//        return userService.getUsersForVacancy(vacancyId);
//    }

}
