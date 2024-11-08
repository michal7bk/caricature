package pl.caricature.images.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.caricature.images.model.ImageResponse;

import java.io.InputStream;

@Service
@AllArgsConstructor
public class ImageService {

    private final OpenApiClient client;


    public ImageResponse createImageEdit(InputStream image, String prompt) {
        final String prefix = "Create a caricature based on the provided image. " +
                " Focus on the real look of the face. ";
        return client.createImageEdit(image, prefix + prompt);
    }
}
