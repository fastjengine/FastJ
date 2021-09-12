package tech.fastj.graphics.textures;

import tech.fastj.engine.FastJEngine;

import tech.fastj.resources.images.ImageResource;

import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Objects;

public class TextureBuilder {

    private final BufferedImage texture;
    private final Rectangle2D textureLocation;

    TextureBuilder(BufferedImage texture, Rectangle2D textureLocation) {
        this.texture = Objects.requireNonNull(texture, "The provided texture image must not be null.");
        this.textureLocation = Objects.requireNonNull(textureLocation, "The provided texture location must not be null.");
    }

    public TexturePaint build() {
        return new TexturePaint(texture, textureLocation);
    }

    static TextureBuilder builder(BufferedImage texture, Rectangle2D textureLocation) {
        return new TextureBuilder(texture, textureLocation);
    }

    static TextureBuilder builder(Path texturePath, Rectangle2D textureLocation) {
        BufferedImage texture = FastJEngine.getResourceManager(ImageResource.class).getResource(texturePath).get();
        return new TextureBuilder(texture, textureLocation);
    }
}
