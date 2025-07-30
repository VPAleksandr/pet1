package pet.project.hh.service;

import pet.project.hh.Dto.UserDto;
import pet.project.hh.Dto.UserRedactDto;
import pet.project.hh.models.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    User getUserById(Long id);

    UserDto getUserDtoById(Long id);

    List<UserDto> getEmployers();

    UserDto getUserByEmail(String email);

    UserDto getUserByNumber(String number);

    void registerEmployer(UserDto userDto);

    void registerApplicant(UserDto userDto);

    boolean isUserHear(String email);

    List<UserDto> getUserByName(String name);

    void updateUser(UserRedactDto userDto);

    void updateUserAvatar(Long userId, String avatarFilename);

    void makeResetPasswdLink(HttpServletRequest request) throws UsernameNotFoundException, UnsupportedEncodingException, MessagingException;

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);
}
