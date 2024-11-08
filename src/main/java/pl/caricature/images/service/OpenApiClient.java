package pl.caricature.images.service;

import pl.caricature.images.model.ImageResponse;

import java.io.InputStream;

public interface OpenApiClient {

    ImageResponse createImageEdit(InputStream image, String prompt);
}
