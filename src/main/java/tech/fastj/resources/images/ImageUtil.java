package tech.fastj.resources.images;

import tech.fastj.engine.FastJEngine;

import tech.fastj.resources.files.FileUtil;

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

import javax.imageio.ImageIO;

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

    public static BufferedImage loadBufferedImage(Path imagePath) {
        Path absoluteResourcePath = imagePath.toAbsolutePath();
        try {
            return ImageIO.read(absoluteResourcePath.toFile());
        } catch (IOException exception) {
            throw new IllegalStateException("An image file was not found at \"" + absoluteResourcePath + "\".", exception);
        }
    }

    public static void writeBufferedImage(BufferedImage image, Path imagePath) {
        Path absoluteResourcePath = imagePath.toAbsolutePath();
        try {
            ImageIO.write(image, FileUtil.getFileExtension(imagePath), absoluteResourcePath.toFile());
        } catch (IOException exception) {
            throw new IllegalStateException("An error occurred while trying to write an image to \"" + absoluteResourcePath + "\".", exception);
        }
    }

    public static void drawToImage(Image reusedImage, Path secondImagePath) {
        Graphics2D graphics2D = (Graphics2D) reusedImage.getGraphics();
        Image imageFromPath;
        try {
            imageFromPath = ImageIO.read(secondImagePath.toAbsolutePath().toFile());
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't get the image at \"" + secondImagePath.toAbsolutePath() + "\"", e);
        }
        graphics2D.setBackground(new Color(0, 0, 0, 0));
        graphics2D.clearRect(0, 0, reusedImage.getWidth(null), reusedImage.getHeight(null));
        graphics2D.drawImage(imageFromPath, null, null);
        graphics2D.dispose();
    }

    public static BufferedImage[] createSpriteSheet(BufferedImage bufferedImage, int horizontalImageCount, int verticalImageCount) {
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

        BufferedImage[] spriteSheet = new BufferedImage[verticalImageCount * horizontalImageCount];
        int spriteSheetIndex = 0;
        for (int i = 0; i < verticalImageCount; i++) {
            for (int j = 0; j < horizontalImageCount; j++) {
                spriteSheet[spriteSheetIndex] = bufferedImage.getSubimage(
                        j * expectedHorizontalImagePixels,
                        i * expectedVerticalImagePixels,
                        expectedHorizontalImagePixels,
                        expectedVerticalImagePixels
                );
                spriteSheetIndex++;
            }
        }

        return spriteSheet;
    }
}
