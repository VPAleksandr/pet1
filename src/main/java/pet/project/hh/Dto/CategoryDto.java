package pet.project.hh.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private Long categoryId;
    private String categoryIdName;
    @NotNull(message = "{Category.must.be.choose}")
    private String name;
}
