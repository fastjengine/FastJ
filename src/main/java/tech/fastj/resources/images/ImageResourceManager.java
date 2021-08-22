package tech.fastj.resources.images;

import tech.fastj.resources.ResourceManager;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class ImageResourceManager extends ResourceManager<ImageResource, BufferedImage> {
    @Override
    public ImageResource createResource(Path resourcePath) {
        return new ImageResource(resourcePath);
    }
}
