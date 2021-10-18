package tech.fastj.graphics.textures;

import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class Textures {
    public static TexturePaint create(Path texturePath, Rectangle2D textureLocation) {
        return TextureBuilder.builder(texturePath, textureLocation).build();
    }

    public static TexturePaint create(BufferedImage texture, Rectangle2D textureLocation) {
        return TextureBuilder.builder(texture, textureLocation).build();
    }
}
