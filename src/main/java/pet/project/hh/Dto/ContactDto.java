package pet.project.hh.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    private Long id;

    @NotNull
    private Long typeId;

    private String typeName;
    private Long resumeId;

    @NotBlank(message = "{Contact.value.required}")
    private String contactValue;
}
