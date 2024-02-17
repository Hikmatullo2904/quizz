package com.uchqun.server.service.service_imp;

import com.uchqun.server.exception.CustomNotFoundException;
import com.uchqun.server.exception.InternalErrorException;
import com.uchqun.server.model.entity.Picture;
import com.uchqun.server.repository.PictureRepository;
import com.uchqun.server.service.interfaces.PictureService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PictureServiceImp implements PictureService {

    private final PictureRepository pictureRepository;

    @Value("${server.upload.url}")
    private String uploadUrl;

    public Picture save(String base64picture) {
        if(base64picture == null) {
            return null;
        }
        byte[] decode = Base64.getDecoder().decode(base64picture);
        String s = savetoFile(decode);
        String[] split = s.split("/.");
        Picture picture = new Picture();
        picture.setPath(split[1]);
        picture.setName(split[0]);
        picture = pictureRepository.save(picture);
        return picture;
    }


    private String savetoFile(byte[] picture) {
        String name = UUID.randomUUID().toString();
        LocalDate now = LocalDate.now();
        String uploadPath = uploadUrl + "/picture/%d/%d/%d".formatted(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth());
        Path path = Path.of(uploadPath, name);

        String result = name + "." + uploadPath;

        try {
            Files.createDirectories(path.getParent());
            Files.write(path, picture, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            throw new InternalErrorException("could not save picture");
        }

        return result;
    }

//    @Override
//    public ResponseEntity<FileUrlResource> get(Long id) {
//        Picture picture = pictureRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Picture not found"));
//        String path = picture.getPath();
//        try {
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + UriEncoder.encode(picture.getName()))
//                    .contentType(MediaType.parseMediaType(picture.getType()))
//                    .body(new FileUrlResource(path));
//        } catch (MalformedURLException e) {
//            throw new InternalErrorException("Failed to get picture");
//        }
//    }

    @Override
    public ResponseEntity<Resource> get(Long id) {
        Picture picture = pictureRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Picture not found"));
        String path = picture.getPath();
        try {
            Resource resource = new FileUrlResource(path);
            if(resource.exists())  {
                MediaType mediaType = MediaType.parseMediaType(resource.getURL().openConnection().getContentType());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(mediaType);
                headers.setContentDispositionFormData("inline; fileName=\"", UriEncoder.encode(picture.getName()));
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                throw new CustomNotFoundException("Picture not found");
            }
        } catch (MalformedURLException e) {
            throw new InternalErrorException("Failed to get picture");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        Picture picture = pictureRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Picture not found"));
        String path = picture.getPath();
        File file = new File(path);
        if(file.exists()) {
            boolean delete = file.delete();
            if(!delete)
                throw new InternalErrorException("Failed to delete picture");
        }
    }
}
