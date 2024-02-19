package com.uchqun.server.service.interfaces;

import com.uchqun.server.model.entity.Picture;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    Picture save(String base64picture);

    void delete(Long id);

    ResponseEntity<Resource> get(Long id);
    Picture save(MultipartFile image);
}
