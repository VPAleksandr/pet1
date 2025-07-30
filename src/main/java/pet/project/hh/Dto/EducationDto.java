package pet.project.hh.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto {

    private Long id;

    private Long resumeId;

    @NotBlank(message = "{Institution.required}")
    private String institution;

    @NotBlank(message = "{Program.required}")
    private String program;

    @NotNull(message = "{Start.date.required}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = "{End.date.required}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @NotBlank(message = "{Degree.required}")
    private String degree;
}
