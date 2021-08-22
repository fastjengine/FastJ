package tech.fastj.graphics.image;

import tech.fastj.graphics.util.ImageUtil;

import tech.fastj.resources.Resource;
import tech.fastj.resources.ResourceState;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class ImageResource implements Resource<BufferedImage> {

    private final Path imagePath;
    private ResourceState resourceState;
    private BufferedImage imageFile;

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
        imageFile = ImageUtil.loadBufferedImage(imagePath);
        resourceState = ResourceState.Loaded;
        return this;
    }

    @Override
    public BufferedImage get() {
        return imageFile;
    }

    @Override
    public void unload() {
        imageFile.flush();
        imageFile = null;
        resourceState = ResourceState.Unloaded;
    }
}
