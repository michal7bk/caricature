package pl.caricature.images.model;

public enum ImageSize {

    SMALL("256x256"),
    MEDIUM("512x512"),
    LARGE("1024x1024");

    private final String size;

    public String getSize() {
        return size;
    }

    ImageSize(String size) {
        this.size = size;
    }
}
