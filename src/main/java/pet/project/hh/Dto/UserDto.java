package pet.project.hh.Dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "{Name.required}")
    private String name;
    private String surname;

    @NotNull(message = "{Age.required}")
    @Max(value = 120, message = "{Age.max.exceeded}")
    private Integer age;

    @Email(message = "{Email.invalid.format}")
    @NotBlank(message = "{Email.required}")
    private String email;

    @NotNull(message = "{Password.required}")
    @NotBlank(message = "{Password.required}")
    @Size(min = 6, max = 24, message = "{Password.size.invalid}")
    private String password;

    @NotBlank(message = "{Phone.number.required}")
    @Pattern(regexp = "^\\+\\d{5,15}$", message = "{Phone.number.invalid.format}")
    private String phoneNumber;
    private String avatar;
    private Long accountType;
    private Boolean enabled;
    private String resetPasswordToken;
}
