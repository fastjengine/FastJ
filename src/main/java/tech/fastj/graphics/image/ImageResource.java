package tech.fastj.graphics.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import tech.fastj.resources.Resource;
import tech.fastj.resources.ResourceState;

public class ImageResource implements Resource<BufferedImage> {

    private final Path imagePath;
    private ResourceState resourceState;
    private BufferedImage imageResource;

    public ImageResource(Path imagePath) {
        this.imagePath = imagePath;
        resourceState = ResourceState.Unloaded;
    }

    @Override
    public ResourceState getResourceState() {
        return resourceState;
    }

    @Override
    public Path getPath() {
        return imagePath;
    }

    @Override
    public ImageResource load() {
        Path absoluteResourcePath = imagePath.toAbsolutePath();

        try {
            imageResource = ImageIO.read(imagePath.toAbsolutePath().toFile());
            resourceState = ResourceState.Loaded;
            return this;
        } catch (IOException exception) {
            throw new IllegalStateException("Couldn't load the image at path \"" + absoluteResourcePath + "\".", exception);
        }
    }

    @Override
    public BufferedImage get() {
        return imageResource;
    }

    @Override
    public void unload() {
        imageResource.flush();
        imageResource = null;
        resourceState = ResourceState.Unloaded;
    }
}
