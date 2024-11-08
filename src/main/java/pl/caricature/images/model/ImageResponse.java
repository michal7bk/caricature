package pl.caricature.images.model;

import lombok.Data;

import java.util.List;

public class ImageResponse {

    public String created;
    public List<ImageUrl> data;

    static class ImageUrl {
        String url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<ImageUrl> getData() {
        return data;
    }

    public void setData(List<ImageUrl> data) {
        this.data = data;
    }
}
