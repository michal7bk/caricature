package pl.caricature.images.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.caricature.images.model.ImageResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;

import static org.apache.http.entity.ContentType.MULTIPART_FORM_DATA;
import static org.springframework.util.ResourceUtils.getFile;
import static pl.caricature.images.model.ImageSize.SMALL;

@Component
public class DallEHttpClient implements OpenApiClient {

    private static final String AUTHORIZATION_PROPERTY = "openApi.key";
    private static final String URL = "https://api.openai.com/v1";

    private final Gson gson;
    private final String authorization;

    @Autowired
    public DallEHttpClient(Environment environment) {
        this.gson = new GsonBuilder().create();
        this.authorization = environment.getProperty(AUTHORIZATION_PROPERTY);
    }

    // Only dall-e-2
    @SneakyThrows
    public ImageResponse createImageEdit(InputStream image, String prompt) {
        final InputStream rgbaImage = convertToRGBA(image);
//        final InputStream maskFile = convertToRGBA(new FileInputStream(getFile("classpath:files/white_image.png")));

        final StringBody promptBody = new StringBody(prompt, MULTIPART_FORM_DATA);
        final StringBody sizeBody = new StringBody(SMALL.getSize(), MULTIPART_FORM_DATA);
        final StringBody requestedNumberOfImages = new StringBody("1", MULTIPART_FORM_DATA);

        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.STRICT);

        builder.addBinaryBody("image", rgbaImage, ContentType.IMAGE_PNG, "image.png");
//        builder.addBinaryBody("mask", maskFile, ContentType.IMAGE_PNG, "mask.png");

        builder.addPart("prompt", promptBody);
        builder.addPart("size", sizeBody);
        builder.addPart("n", requestedNumberOfImages);

        final HttpEntity entity = builder.build();
        HttpPost post = new HttpPost();
        post.setURI(URI.create(URL + "/images/edits"));
        post.setEntity(entity);
        post.addHeader("Authorization", "Bearer " + this.authorization);
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
             return client.execute(post, response -> {
                final String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(" Response " + responseBody);
                return gson.fromJson(responseBody, ImageResponse.class);
            });
        }
    }

    @SneakyThrows
    private InputStream convertToRGBA(InputStream file) {
        BufferedImage originalImage = ImageIO.read(file);
        BufferedImage rgbaImage = convertToRGBA(originalImage);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(rgbaImage, "png", output);
        return new ByteArrayInputStream(output.toByteArray());
    }

    public static BufferedImage convertToRGBA(BufferedImage image) {
        BufferedImage rgbaImage = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        rgbaImage.getGraphics().drawImage(image, 0, 0, null);
        return rgbaImage;
    }


}
