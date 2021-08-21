package tech.fastj.graphics.util;

import tech.fastj.engine.FastJEngine;

import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.nio.file.Path;

public class ImageUtil {

    public static VolatileImage createVolatileImage(int width, int height) {
        try {
            return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleVolatileImage(
                    width,
                    height,
                    new ImageCapabilities(true),
                    Transparency.TRANSLUCENT
            );
        } catch (AWTException e) {
            throw new IllegalStateException("Issue creating volatile image", e);
        }
    }

    public static BufferedImage createBufferedImage(int width, int height) {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(
                width,
                height,
                Transparency.TRANSLUCENT
        );
    }

    public static void drawToImage(Image reusedImage, Path pathToSecondImage) {
        Graphics2D graphics2D = (Graphics2D) reusedImage.getGraphics();
        FastJEngine.log(pathToSecondImage.toAbsolutePath().toString());
        Image imageFromPath;
        try {
            imageFromPath = ImageIO.read(pathToSecondImage.toAbsolutePath().toFile());
        } catch (IOException e) {
            throw new IllegalStateException("e", e);
        }
        graphics2D.setBackground(new Color(0, 0, 0, 0));
        graphics2D.clearRect(0, 0, reusedImage.getWidth(null), reusedImage.getHeight(null));
        graphics2D.drawImage(imageFromPath, null, null);
        graphics2D.dispose();
    }

    public static BufferedImage[][] createSpriteSheet(BufferedImage bufferedImage, int horizontalImageCount, int verticalImageCount) {
        int bufferedImageWidth = bufferedImage.getWidth();
        int expectedHorizontalImagePixels = bufferedImageWidth / horizontalImageCount;
        float actualHorizontalImagePixels = bufferedImageWidth / (float) horizontalImageCount;

        if (expectedHorizontalImagePixels != actualHorizontalImagePixels) {
            FastJEngine.warning(
                    String.format(
                            "The horizontal image count given (%d) may cause pixel loss, as not all images will receive the same amount. The calculated pixel count %f is derived from \"%d / %d\".",
                            horizontalImageCount,
                            actualHorizontalImagePixels,
                            bufferedImageWidth,
                            horizontalImageCount
                    )
            );
        }

        int bufferedImageHeight = bufferedImage.getHeight();
        int expectedVerticalImagePixels = bufferedImageHeight / verticalImageCount;
        float actualVerticalImagePixels = bufferedImageHeight / (float) verticalImageCount;

        if (expectedVerticalImagePixels != actualVerticalImagePixels) {
            FastJEngine.warning(
                    String.format(
                            "The vertical image count given (%d) may cause pixel loss, as not all images will receive the same amount. The calculated pixel count %f is derived from \"%d / %d\".",
                            verticalImageCount,
                            actualVerticalImagePixels,
                            bufferedImageHeight,
                            verticalImageCount
                    )
            );
        }

        BufferedImage[][] spriteSheet = new BufferedImage[verticalImageCount][horizontalImageCount];
        for (int i = 0; i < spriteSheet.length; i++) {
            for (int j = 0; j < spriteSheet[0].length; j++) {
                spriteSheet[i][j] = bufferedImage.getSubimage(
                        j * expectedHorizontalImagePixels,
                        i * expectedVerticalImagePixels,
                        expectedHorizontalImagePixels,
                        expectedVerticalImagePixels
                );
            }
        }

        return spriteSheet;
    }
}
