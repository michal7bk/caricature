package pl.caricature.images.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.caricature.images.model.ImageResponse;
import pl.caricature.images.service.ImageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class ImagesController {

    private final ImageService imageService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ImageResponse createImageEdit(@RequestParam("file") MultipartFile file,
                                         @RequestParam("prompt") String prompt) {
        InputStream image = readImage(file);
        return imageService.createImageEdit(image, prompt);
    }

    private static InputStream readImage(MultipartFile file) {
        try {
            byte[] byteArr = file.getBytes();
            return new ByteArrayInputStream(byteArr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
