package pet.project.hh.Dto;

import pet.project.hh.models.RespondedApplicant;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private RespondedApplicant respondedApplicant;
    @NotNull(message = "{Message.content.required}")
    private String content;
    private LocalDateTime localDateTime;
}
