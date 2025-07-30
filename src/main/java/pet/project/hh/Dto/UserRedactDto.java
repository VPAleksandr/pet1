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
public class UserRedactDto {
    private Long id;

    @NotBlank(message = "{Name.required}")
    private String name;
    private String surname;

    @NotNull(message = "{Age.required}")
    @Max(value = 120, message = "{Age.max.exceeded}")
    private Integer age;
}
