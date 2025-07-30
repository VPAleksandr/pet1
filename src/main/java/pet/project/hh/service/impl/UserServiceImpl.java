package pet.project.hh.service.impl;

import pet.project.hh.Dto.UserDto;
import pet.project.hh.Dto.UserRedactDto;
import pet.project.hh.models.User;
import pet.project.hh.repository.AccountTypeRepository;
import pet.project.hh.repository.UserRepository;
import pet.project.hh.service.EmailService;
import pet.project.hh.service.UserService;
import pet.project.hh.exceptions.UserNotFoundException;
import pet.project.hh.utils.Utility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final AccountTypeRepository accountTypeRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder encoder;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDto getUserDtoById(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .age(user.getAge())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .accountType(user.getType().getId())
                .build();
    }

    @Override
    public List<UserDto> getEmployers() {
        List<User> list = userRepository.findAllEmployers();
        return list.stream()
                .map(e -> UserDto.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .age(e.getAge())
                        .email(e.getEmail())
                        .accountType(e.getType().getId())
                        .avatar(e.getAvatar())
                        .phoneNumber(e.getPhoneNumber())
                        .surname(e.getSurname())
                        .build())
                .toList();
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .accountType(user.getType().getId())
                .avatar(user.getAvatar())
                .phoneNumber(user.getPhoneNumber())
                .surname(user.getSurname())
                .build();
    }

    @Override
    public UserDto getUserByNumber(String number) {
        User user = userRepository.findByPhoneNumber(number)
                .orElseThrow(UserNotFoundException::new);
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .accountType(user.getType().getId())
                .avatar(user.getAvatar())
                .phoneNumber(user.getPhoneNumber())
                .surname(user.getSurname())
                .build();
    }

    @Override
    public List<UserDto> getUserByName(String name) {
        List<User> list = userRepository.findAllByNameExample(name);
        return list.stream()
                .map(e -> UserDto.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .age(e.getAge())
                        .email(e.getEmail())
                        .accountType(e.getType().getId())
                        .avatar(e.getAvatar())
                        .phoneNumber(e.getPhoneNumber())
                        .surname(e.getSurname())
                        .build())
                .toList();
    }

    @Override
    public void registerEmployer(UserDto userDto) {
        if (isUserHear(userDto.getEmail())) {
            throw new IllegalArgumentException("Пользователь с такой почтой уже существует");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setSurname(" ");
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setType(accountTypeRepository.findById(2L).get());
        user.setAvatar(" ");
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void registerApplicant(UserDto userDto) {
        if (isUserHear(userDto.getEmail())) {
            throw new IllegalArgumentException("Пользователь с такой почтой уже существует");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEnabled(true);
        user.setAvatar(" ");
        user.setType(accountTypeRepository.findById(1L).get());
        userRepository.save(user);
    }

    @Override
    public boolean isUserHear(String email) {
        return userRepository.findAll()
                .stream()
                .anyMatch(e -> e.getEmail().equals(email));
    }

    @Override
    public void updateUser(UserRedactDto userDto) {
        User user = userRepository.findById(userDto.getId()).get();
        user.setName(userDto.getName());
        if (user.getSurname() != null) {
            user.setSurname(userDto.getSurname());
        } else {
            user.setSurname(" ");
        }
        user.setAge(userDto.getAge());
        userRepository.save(user);
    }

    @Override
    public void updateUserAvatar(Long userId, String avatarFilename) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        user.setAvatar(avatarFilename);
        userRepository.save(user);
    }

    @Override
    public void makeResetPasswdLink(HttpServletRequest request) throws UsernameNotFoundException, UnsupportedEncodingException, MessagingException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        updateResetPasswordToken(token, email);
        String resetPasswordLink = Utility.getSiteURL(request) + "/auth/reset_password?token=" + token;
        emailService.sendEmail(email, resetPasswordLink);
    }

    private void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Could not find any user with the email " + email));
        user.setResetPasswordToken(token);
        userRepository.saveAndFlush(user);
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        String encodedPassword = encoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.saveAndFlush(user);
    }
}
