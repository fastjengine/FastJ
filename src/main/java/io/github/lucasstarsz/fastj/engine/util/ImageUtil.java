package io.github.lucasstarsz.fastj.engine.util;

import io.github.lucasstarsz.fastj.engine.FastJEngine;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * Class that provides supplementary methods for working with images.
 *
 * @author Andrew Dey
 * @version 0.3.2a
 */
public class ImageUtil {

    private static final String imageReadError = CrashMessages.theGameCrashed("an image file reading error.");
    private static final String imageWriteError = CrashMessages.theGameCrashed("an image file writing error.");

    /**
     * Returns the specified image, resized according to the specified width and height.
     *
     * @param source    The BufferedImage to be resized.
     * @param newWidth  The width of the new image.
     * @param newHeight The height of the new image.
     * @return A {@code BufferedImage} that contains the resized image.
     * @see BufferedImage
     */
    public static BufferedImage resizeImage(BufferedImage source, int newWidth, int newHeight) {
        Image resizedImg = source.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage result = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(resizedImg, 0, 0, null);
        g2d.dispose();

        return result;
    }

    /**
     * Splits the specified image into the specified amount of rows and columns, then writes each image to a file.
     *
     * @param source         The source image to split.
     * @param destPath       The destination path of the images that will be written.
     * @param rowsAndColumns The amount of rows and columns to split the image into.
     * @see BufferedImage
     */
    public static void writeSplitImage(BufferedImage source, String destPath, int rowsAndColumns) {
        writeSplitImage(source, destPath, rowsAndColumns, rowsAndColumns);
    }

    /**
     * Splits the specified image into the specified amount of rows and columns, then writes each image to a file.
     *
     * @param source   The source image to split.
     * @param destPath The destination path of the images that will be written.
     * @param rows     The amount of rows to split the image into.
     * @param columns  The amount of columns to split the image into.
     * @see BufferedImage
     */
    public static void writeSplitImage(BufferedImage source, String destPath, int rows, int columns) {
        // check for file extension
        int dotIndex = destPath.lastIndexOf('.');

        if (dotIndex == -1) {
            FastJEngine.error(imageWriteError, new IOException("The path: \"" + destPath + "\" doesn't contain a file extension."));
        }

        // write images
        BufferedImage[][] splitImage = splitImage(source, rows, columns);
        int imgNum = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                try {
                    ImageIO.write(
                            splitImage[i][j], destPath.substring(dotIndex + 1),
                            new File(destPath.substring(0, dotIndex) + '_' + imgNum + '.' + destPath.substring(dotIndex + 1))
                    );
                } catch (IOException e) {
                    FastJEngine.error(imageWriteError, e);
                }

                imgNum++;
            }
        }

        // clear split image data/references
        for (BufferedImage[] arr : splitImage) {
            for (BufferedImage img : arr) {
                img.flush();
            }
        }
    }

    /**
     * Splits the specified image into the specified amount of rows and columns, and returns the result.
     *
     * @param source         The source image to split.
     * @param rowsAndColumns The amount of rows and columns to split the image into.
     * @return The split image.
     * @see BufferedImage
     */
    public static BufferedImage[][] splitImage(BufferedImage source, int rowsAndColumns) {
        return splitImage(source, rowsAndColumns, rowsAndColumns);
    }

    /**
     * Splits the specified image into the specified amount of rows and columns, and returns the result.
     *
     * @param source  The source image to split.
     * @param rows    The amount of rows to split the image into.
     * @param columns The amount of columns to split the image into.
     * @return The split image.
     * @see BufferedImage
     */
    public static BufferedImage[][] splitImage(BufferedImage source, int rows, int columns) {
        final BufferedImage[][] result = new BufferedImage[rows][columns];
        int splitImageWidth = (int) (source.getWidth() / (double) rows);
        int splitImageHeight = (int) (source.getHeight() / (double) columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = source.getSubimage(j * splitImageWidth, i * splitImageHeight, splitImageWidth, splitImageHeight);
            }
        }

        return result;
    }

    /**
     * Gets the image at the specified path.
     *
     * @param path The path to look for the image at.
     * @return The resultant {@code BufferedImage}.
     * @see BufferedImage
     */
    public static BufferedImage getImage(String path) {
        BufferedImage resultImage = null;

        try {
            resultImage = ImageIO.read(ImageUtil.class.getResourceAsStream(path));

            // force load image
            Image forceLoader;
            do {
                forceLoader = new ImageIcon(resultImage).getImage();
            } while (forceLoader == null);

        } catch (IOException e) {
            FastJEngine.error(imageReadError, e);
        }

        return resultImage;
    }

    /**
     * Creates and returns a copy of the specified image.
     *
     * @param source The image to be copied.
     * @return A copy of the original image.
     * @see BufferedImage
     */
    public static BufferedImage copyImage(BufferedImage source) {
        ColorModel cm = source.getColorModel();
        WritableRaster raster = source.copyData(source.getRaster().createCompatibleWritableRaster());

        return new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
    }
}
