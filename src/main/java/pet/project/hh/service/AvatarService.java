package pet.project.hh.service;

import pet.project.hh.Dto.UserAvatarDto;
import org.springframework.http.ResponseEntity;

public interface AvatarService {
    String saveImage(UserAvatarDto movieImageDto);

    ResponseEntity<?> findByName(String imageName);

    ResponseEntity<?> findById(long imageId);

    ResponseEntity<?> findByMovieId(long movieId);
}
