package pl.caricature.images.service;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

import static org.apache.http.entity.ContentType.MULTIPART_FORM_DATA;
import static org.springframework.util.ResourceUtils.getFile;

@Component
public class DallEHttpClient implements OpenApiClient {

    private static final String AUTHORIZATION_PROPERTY = "openApi.key";
    private static final String DEFAULT_SIZE = "256x256";
    private static final String URL = "https://api.openai.com/v1";

    private final CloseableHttpClient httpClient;
    private final String authorization;

    @Autowired
    public DallEHttpClient(Environment environment) {
        httpClient = HttpClientBuilder.create().build();

        this.authorization = environment.getProperty(AUTHORIZATION_PROPERTY);
    }

    // Only dall-e-2
    @SneakyThrows
    public void createImageEdit(String prompt) {
        final File file = convertToRGBA(getFile("classpath:files/image.png"));

        final StringBody promptBody = new StringBody(prompt, MULTIPART_FORM_DATA);
        final StringBody sizeBody = new StringBody(DEFAULT_SIZE, MULTIPART_FORM_DATA);

        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.STRICT);

        builder.addBinaryBody("image", file, ContentType.IMAGE_PNG, "image.png");

        builder.addPart("prompt", promptBody);
        builder.addPart("size", sizeBody);
        final HttpEntity entity = builder.build();
        HttpPost post = new HttpPost();
        post.setURI(URI.create(URL + "/images/edits"));
        post.setEntity(entity);
        post.addHeader("Authorization", "Bearer " + this.authorization);
        try (CloseableHttpClient client = HttpClientBuilder.create()
                .build()) {

            client.execute(post, response -> {
                System.out.println(response);
                return null;
            });
        }
    }

    @SneakyThrows
    private File convertToRGBA(File file) {
        BufferedImage originalImage = ImageIO.read(file);
        BufferedImage rgbaImage = convertToRGBA(originalImage);
        ImageIO.write(rgbaImage, "png", new File("converted_image_rgba.png"));
        return getFile("converted_image_rgba.png");
    }

    public static BufferedImage convertToRGBA(BufferedImage image) {
        BufferedImage rgbaImage = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        rgbaImage.getGraphics().drawImage(image, 0, 0, null);
        return rgbaImage;
    }


}
