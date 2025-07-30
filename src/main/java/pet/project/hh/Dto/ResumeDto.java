package pet.project.hh.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {
    private Long id;
    private Long applicantId;
    private String applicantName;

    @NotNull(message = "{Category.must.be.choose}")
    private Long categoryId;
    private String categoryName;

    @NotBlank(message = "{Position.required}")
    private String name;

    @NotNull
    @PositiveOrZero(message = "{Salary.required}")
    private Double salary;

    @NotNull
    private boolean isActive;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String updatedTimeString;

    @Valid
    private List<ContactDto> contacts = new ArrayList<>();
    @Valid
    private List<WorkExperienceDto> workExperiences = new ArrayList<>();
    @Valid
    private List<EducationDto> educations = new ArrayList<>();
}
