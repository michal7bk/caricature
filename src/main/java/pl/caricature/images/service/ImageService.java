package pl.caricature.images.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.caricature.images.model.Image;

@Service
@AllArgsConstructor
public class ImageService {

    private final OpenApiClient client;

    public Image createImageEdit() {
        String prompt = "Create caricature based on provided image";
        client.createImageEdit(prompt);
        return null;
    }
}
