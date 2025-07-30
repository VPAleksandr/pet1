package pet.project.hh.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {


    String uploadAvatar(MultipartFile file);

    ResponseEntity<?> getAvatar(String filename);
}
