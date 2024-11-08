package pl.caricature.images.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import pl.caricature.images.model.ImageResponse;

class DallEHttpClientTest {

    DallEHttpClient clint = new DallEHttpClient(new MockEnvironment());

    @Test
    void shouldMapResponseToDto() {
        String exampleResponse = "{\n" +
                "  \"created\": 1731080379,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"url\": \"https://oaidalleapiprodscus.blob.core.windows.net/private/org-owUo1iGDmNF8Xg6asTlph8jT/user-VFF8CdiRo6HPsyZcQcDr4qDA/img-tYgeglai34fOp0N7y7RCXlph.png?st=2024-11-08T14%3A39%3A39Z&se=2024-11-08T16%3A39%3A39Z&sp=r&sv=2024-08-04&sr=b&rscd=inline&rsct=image/png&skoid=d505667d-d6c1-4a0a-bac7-5c84a87759f8&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2024-11-07T16%3A18%3A20Z&ske=2024-11-08T16%3A18%3A20Z&sks=b&skv=2024-08-04&sig=Ux2xWMLAl1d8mqrePmypMhJvTA7zKg6s07Aw5yH7/bk%3D\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Gson gson = new GsonBuilder().create();

        final ImageResponse imageResponse = gson.fromJson(exampleResponse, ImageResponse.class);

    }

}