package pl.caricature.images.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.caricature.images.model.Image;
import pl.caricature.images.service.ImageService;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class ImagesController {

    private final ImageService imageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Image createImageEdit() {
        return imageService.createImageEdit();
    }
}
