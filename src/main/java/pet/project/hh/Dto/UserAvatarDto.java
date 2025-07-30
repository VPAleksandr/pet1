package pet.project.hh.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserAvatarDto {
    private MultipartFile file;
    private Long userId;
}
