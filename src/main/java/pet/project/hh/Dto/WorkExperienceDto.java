package pet.project.hh.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceDto {
    private Long id;
    private Long resumeId;

    @NotNull(message = "{Years.worked.required}")
    @PositiveOrZero(message = "{Years.worked.positive}")
    private Integer years;

    @NotBlank(message = "{Company.name.required}")
    private String companyName;

    @NotBlank(message = "{Position.required}")
    private String position;
    private String responsibilities;
}
