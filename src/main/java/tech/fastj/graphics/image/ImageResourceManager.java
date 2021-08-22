package tech.fastj.graphics.image;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

import tech.fastj.resources.ResourceManager;

public class ImageResourceManager extends ResourceManager<ImageResource, BufferedImage> {
    @Override
    public ImageResource createResource(Path resourcePath) {
        return new ImageResource(resourcePath);
    }
}
