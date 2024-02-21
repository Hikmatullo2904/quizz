package com.uchqun.server.service.service_imp;

import com.uchqun.server.exception.CustomNotFoundException;
import com.uchqun.server.exception.InternalErrorException;
import com.uchqun.server.model.entity.Picture;
import com.uchqun.server.repository.PictureRepository;
import com.uchqun.server.service.interfaces.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PictureServiceImp implements PictureService {

    private final PictureRepository pictureRepository;

    @Value("${server.upload.url}")
    private String uploadUrl;

    public Picture save(MultipartFile image)  {
        Picture picture = new Picture();
        picture.setName(image.getOriginalFilename());
        picture.setType(image.getContentType());
        LocalDate now = LocalDate.now();
        String path = uploadUrl + "/picture/%d/%d/%d".formatted(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth());

        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        picture = pictureRepository.save(picture);

        String filePath = path + picture.getId() + "$" + image.getOriginalFilename();
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save compressed image to the server", e);
        }

        picture.setPath(filePath);
        return pictureRepository.save(picture);


    }

    public Picture save(String base64picture) {
        if(base64picture == null) {
            return null;
        }
        String base64Data = base64picture.replaceFirst("^data:image/[^;]+;base64,", "");

        Pattern pattern = Pattern.compile("^data:(image/([^;]+));base64,");
        Matcher matcher = pattern.matcher(base64picture);
        String contentType = "image/png";
        String extension = "png";
        if (matcher.find()) {
             contentType = matcher.group(1); // Extracted content type (e.g., "image/png")
            String[] parts = contentType.split("/");
             extension = parts[1]; // Extracted extension (e.g., "png")
        }

        byte[] decode = Base64.getDecoder().decode(base64Data);
        String s = savetoFile(decode, extension);
        String[] split = s.split(" ");
        Picture picture = new Picture();
        picture.setPath(split[0] + "/" + split[1]);
        picture.setName(split[1]);
        picture.setType(contentType);
        picture.setExt(extension);
        picture = pictureRepository.save(picture);
        return picture;
    }


    private String savetoFile(byte[] picture, String extension) {
        String name = UUID.randomUUID().toString() + "." + extension;
        LocalDate now = LocalDate.now();
        String uploadPath = uploadUrl + "/picture/%d/%d/%d".formatted(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth());
        Path path = Path.of(uploadPath, name);

        String result = uploadPath + " " + name ;


        try {
            Files.createDirectories(path.getParent());
            Files.write(path, picture, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            throw new InternalErrorException("could not save picture");
        }

        return result;
    }

    public ResponseEntity<FileUrlResource> get(Long attachmentId) {
        Optional<Picture> picture = pictureRepository.findById(attachmentId);
        if (picture.isPresent()) {
            Picture attachment = picture.get();

            try {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" +
                                UriEncoder.encode(attachment.getName()))
                        .contentType(MediaType.parseMediaType(attachment.getType()))
                        .body(
                                new FileUrlResource(
                                        attachment.getPath())
                        );
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public void delete(Long id) {
        if(id == null)
            return;
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
