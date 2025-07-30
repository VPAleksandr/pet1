package pet.project.hh.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactTypeDto {
    private Long id;
    @NotNull(message = "{Contact.type.required}")
    private String type;
}
