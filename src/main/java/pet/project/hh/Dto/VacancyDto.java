package pet.project.hh.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDto {
    private Long id;
    private Long authorId;
    private String authorName;

    @NotBlank(message = "{Position.required}")
    private String name;
    private String description;
    private String category;

    @NotNull(message = "{Category.must.be.choose}")
    private Long categoryId;
    private double salary;

    @NotNull
    @PositiveOrZero(message = "{Experience.min.required}")
    private int experienceMin;

    @NotNull
    @PositiveOrZero(message = "{Experience.max.required}")
    private int experienceMax;
    private boolean isActive;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String updatedTimeString;
}
