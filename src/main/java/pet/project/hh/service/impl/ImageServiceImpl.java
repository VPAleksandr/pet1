package pet.project.hh.service.impl;

import pet.project.hh.service.ImageService;
import pet.project.hh.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final FileUtil fileUtil;

    private static final String AVATAR_SUBDIR = "avatars/";

    @Override
    public String uploadAvatar(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Файл аватара не может быть пустым");
        }
        return fileUtil.saveUploadFile(file, AVATAR_SUBDIR);
    }

    @Override
    public ResponseEntity<?> getAvatar(String filename) {
        return fileUtil.getOutputFile(filename, AVATAR_SUBDIR, MediaType.IMAGE_JPEG);
    }
}