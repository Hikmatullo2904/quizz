package com.uchqun.server.controller;

import com.uchqun.server.service.interfaces.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/picture")
@RequiredArgsConstructor
public class PictureController {
    private final PictureService pictureService;

    @GetMapping("/{id}")
    public ResponseEntity<FileUrlResource> get(@PathVariable Long id) {
        return pictureService.get(id);

    }
}
