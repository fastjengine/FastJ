package tech.fastj.resources.models;

import tech.fastj.engine.CrashMessages;
import tech.fastj.engine.FastJEngine;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;

import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.textures.Textures;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.resources.files.FileUtil;
import tech.fastj.resources.images.ImageResource;
import tech.fastj.resources.images.ImageUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MtlUtil {

    private static final String LineSeparator = System.lineSeparator();

    private MtlUtil() {
        throw new java.lang.IllegalStateException();
    }

    public static void parse(Polygon2D polygon, Path materialPath, String materialName, boolean isFill) {
        if (materialName.isBlank()) {
            return;
        }

        List<String> lines = FileUtil.readFileLines(materialPath);
        int materialIndex = lines.indexOf(
                lines.stream()
                        .filter(line -> line.startsWith(ParsingKeys.NewMaterial + " " + materialName))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Couldn't find material \"" + materialName + "\" in file \"" + materialPath.toAbsolutePath() + "\"."))
        );

        for (int i = materialIndex + 1; i < lines.size(); i++) {
            String[] tokens = lines.get(i).split("\\s+");
            switch (tokens[0]) {
                case ParsingKeys.AmbientColor: {
                    parseColor(
                            polygon,
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]),
                            isFill
                    );
                    break;
                }
                case ParsingKeys.Transparency: {
                    parseColorAlpha(polygon, Float.parseFloat(tokens[1]), isFill);
                    break;
                }
                case ParsingKeys.TextureImage: {
                    String materialPathString = materialPath.toString();
                    String materialFileName = materialPath.getFileName().toString();
                    Path imagePath = Path.of(materialPathString.substring(0, materialPathString.indexOf(materialFileName)) + tokens[1]);
                    parseImageTexture(polygon, imagePath);
                    break;
                }
                case ParsingKeys.NewMaterial: {
                    return;
                }
                case ParsingKeys.DiffuseColor:
                case ParsingKeys.SpecularColor:
                case ParsingKeys.SpecularExponent:
                case ParsingKeys.Empty:
                default: {
                    break;
                }
            }
        }
    }

    public static void parseColor(Polygon2D polygon, float red, float green, float blue, boolean isFill) {
        Color color = new Color((int) (red * 255), (int) (green * 255), (int) (blue * 255));
        if (isFill) {
            polygon.setFill(color);
        } else {
            polygon.setOutlineColor(color);
        }
    }

    public static void parseColorAlpha(Polygon2D polygon, float alpha, boolean isFill) {
        if (isFill) {
            Color color = (Color) polygon.getFill();
            polygon.setFill(
                    new Color(
                            color.getRed(),
                            color.getGreen(),
                            color.getBlue(),
                            (int) (alpha * 255)
                    )
            );
        } else {
            Color color = polygon.getOutlineColor();
            polygon.setOutlineColor(
                    new Color(
                            color.getRed(),
                            color.getGreen(),
                            color.getBlue(),
                            (int) (alpha * 255)
                    )
            );
        }
    }

    public static void parseImageTexture(Polygon2D polygon, Path imagePath) {
        FastJEngine.getResourceManager(ImageResource.class).loadResource(imagePath);
        polygon.setFill(Textures.create(imagePath, DrawUtil.createRect(polygon.getBounds())));
    }

    public static void write(Path destinationPath, Model2D model) {
        Path destinationPathWithoutSpaces = Path.of(destinationPath.toString().replace(' ', '_'));
        StringBuilder fileContents = new StringBuilder();
        writeTimestamp(fileContents);

        for (int i = 0; i < model.getPolygons().length; i++) {
            Polygon2D polygon2D = model.getPolygons()[i];
            writeMaterial(fileContents, polygon2D, destinationPathWithoutSpaces, i + 1);
        }

        try {
            Files.writeString(destinationPathWithoutSpaces, fileContents, StandardCharsets.US_ASCII);
        } catch (IOException exception) {
            throw new IllegalStateException(CrashMessages.theGameCrashed("a .mtl file writing error."), exception);
        }
    }

    private static void writeMaterial(StringBuilder fileContents, Polygon2D polygon, Path destinationPath, int materialIndex) {
        switch (polygon.getRenderStyle()) {
            case Fill: {
                writeFillMaterial(fileContents, polygon, destinationPath, materialIndex);
                break;
            }
            case Outline: {
                writeOutlineMaterial(fileContents, polygon, materialIndex);
                break;
            }
            case FillAndOutline: {
                writeFillMaterial(fileContents, polygon, destinationPath, materialIndex);
                writeOutlineMaterial(fileContents, polygon, materialIndex);
                break;
            }
        }
    }

    private static void writeFillMaterial(StringBuilder fileContents, Polygon2D polygon, Path destinationPath, int materialIndex) {
        fileContents.append(ParsingKeys.NewMaterial)
                .append(' ')
                .append("Polygon2D_material_fill_")
                .append(materialIndex)
                .append(LineSeparator);

        Paint material = polygon.getFill();
        if (material instanceof LinearGradientPaint || material instanceof RadialGradientPaint) {
            writeGradientMaterial(fileContents, polygon, destinationPath, materialIndex);
        } else if (material instanceof Color) {
            writeColorMaterial(fileContents, (Color) material);
        } else if (material instanceof TexturePaint) {
            writeTextureMaterial(fileContents, (TexturePaint) material, destinationPath, materialIndex);
        } else {
            FastJEngine.error(
                    CrashMessages.UnimplementedMethodError.errorMessage,
                    new UnsupportedOperationException(
                            "Writing paints other than LinearGradientPaint, RadialGradientPaint, Color, or TexturePaint is not supported."
                                    + System.lineSeparator()
                                    + "Check the github to confirm you are on the latest version, as that version may have more implemented features."
                    )
            );
        }
    }

    private static void writeOutlineMaterial(StringBuilder fileContents, Polygon2D polygon, int materialIndex) {
        fileContents.append(ParsingKeys.NewMaterial)
                .append(' ')
                .append("Polygon2D_material_outline_")
                .append(materialIndex)
                .append(LineSeparator);
        writeColorMaterial(fileContents, polygon.getOutlineColor());
    }

    private static void writeGradientMaterial(StringBuilder fileContents, Polygon2D polygon, Path destinationPath, int materialIndex) {
        writeDefaultColorValues(fileContents);

        int extensionIndex = destinationPath.toString().indexOf(FileUtil.getFileExtension(destinationPath));
        Path texturePath = Path.of(destinationPath.toString().substring(0, extensionIndex - 1).replace(' ', '_') + "_gradient_" + materialIndex + ".png");

        Pointf polygonSize = Pointf.subtract(polygon.getBound(Boundary.BottomRight), polygon.getBound(Boundary.TopLeft)).multiply(2f);
        BufferedImage bufferedImage = ImageUtil.createBufferedImage((int) (polygonSize.x / 2) + 1, (int) (polygonSize.y / 2) + 1);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setPaint(polygon.getFill());
        graphics2D.translate(-bufferedImage.getWidth(), -bufferedImage.getHeight());
        graphics2D.fillRect(0, 0, (int) polygonSize.x + 1, (int) polygonSize.y + 1);
        graphics2D.dispose();

        ImageUtil.writeBufferedImage(bufferedImage, texturePath);
        fileContents.append(ParsingKeys.TextureImage)
                .append(' ')
                .append(texturePath)
                .append(LineSeparator)
                .append(LineSeparator);
    }

    private static void writeTextureMaterial(StringBuilder fileContents, TexturePaint material, Path destinationPath, int materialIndex) {
        writeDefaultColorValues(fileContents);

        Path texturePath;
        try {
            texturePath = FastJEngine.getResourceManager(ImageResource.class).tryFindPathOfResource(material.getImage()).toAbsolutePath();
            if (texturePath.toString().contains("\\s+")) {
                throw new IllegalStateException("The file path for a texture image in .mtl cannot contain whitespace.");
            }
        } catch (IllegalArgumentException exception) {
            int extensionIndex = destinationPath.toString().indexOf(FileUtil.getFileExtension(destinationPath));
            texturePath = Path.of(destinationPath.toString().substring(0, extensionIndex - 1) + "_image_" + materialIndex + ".png");
            ImageUtil.writeBufferedImage(material.getImage(), texturePath);
        }

        fileContents.append(ParsingKeys.TextureImage)
                .append(' ')
                .append(texturePath)
                .append(LineSeparator)
                .append(LineSeparator);
    }

    private static void writeDefaultColorValues(StringBuilder fileContents) {
        fileContents.append(ParsingKeys.AmbientColor)
                .append(' ')
                .append(String.format("%6f", 1f))
                .append(' ')
                .append(String.format("%6f", 1f))
                .append(' ')
                .append(String.format("%6f", 1f))
                .append(LineSeparator);
        fileContents.append(ParsingKeys.DiffuseColor)
                .append(' ')
                .append(String.format("%6f", 1f))
                .append(' ')
                .append(String.format("%6f", 1f))
                .append(' ')
                .append(String.format("%6f", 1f))
                .append(LineSeparator);
        writeSpecularValues(fileContents);
        fileContents.append(ParsingKeys.Transparency)
                .append(' ')
                .append(String.format("%6f", 1f))
                .append(LineSeparator);
        fileContents.append(ParsingKeys.IlluminationMode)
                .append(' ')
                .append(1)
                .append(LineSeparator);
    }

    private static void writeTimestamp(StringBuilder fileContents) {
        fileContents.append("# Generated by the FastJ Game Engine https://github.com/fastjengine/FastJ")
                .append(LineSeparator)
                .append("# Timestamp: ")
                .append(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                .append(LineSeparator)
                .append(LineSeparator);
    }

    private static void writeColorMaterial(StringBuilder fileContents, Color colorMaterial) {
        fileContents.append(ParsingKeys.AmbientColor)
                .append(' ')
                .append(String.format("%6f", Maths.normalize(colorMaterial.getRed(), 0f, 255f)))
                .append(' ')
                .append(String.format("%6f", Maths.normalize(colorMaterial.getGreen(), 0f, 255f)))
                .append(' ')
                .append(String.format("%6f", Maths.normalize(colorMaterial.getBlue(), 0f, 255f)))
                .append(LineSeparator);
        fileContents.append(ParsingKeys.DiffuseColor)
                .append(' ')
                .append(String.format("%6f", Maths.normalize(colorMaterial.getRed(), 0f, 255f)))
                .append(' ')
                .append(String.format("%6f", Maths.normalize(colorMaterial.getGreen(), 0f, 255f)))
                .append(' ')
                .append(String.format("%6f", Maths.normalize(colorMaterial.getBlue(), 0f, 255f)))
                .append(LineSeparator);

        writeSpecularValues(fileContents);

        fileContents.append(ParsingKeys.Transparency)
                .append(' ')
                .append(String.format("%6f", Maths.normalize(colorMaterial.getAlpha(), 0f, 255f)))
                .append(LineSeparator);
        fileContents.append(ParsingKeys.IlluminationMode)
                .append(' ')
                .append(1)
                .append(LineSeparator)
                .append(LineSeparator);
    }

    private static void writeSpecularValues(StringBuilder fileContents) {
        fileContents.append(ParsingKeys.SpecularColor)
                .append(' ')
                .append(String.format("%6f", 0f))
                .append(' ')
                .append(String.format("%6f", 0f))
                .append(' ')
                .append(String.format("%6f", 0f))
                .append(LineSeparator);
        fileContents.append(ParsingKeys.SpecularExponent)
                .append(' ')
                .append(String.format("%6f", 1f))
                .append(LineSeparator);
    }

    public static class ParsingKeys {
        private ParsingKeys() {
            throw new java.lang.IllegalStateException();
        }

        public static final String Empty = "";
        public static final String NewMaterial = "newmtl";
        public static final String AmbientColor = "Ka";
        public static final String DiffuseColor = "Kd";
        public static final String SpecularColor = "Ks";
        public static final String SpecularExponent = "Ns";
        public static final String Transparency = "d";
        public static final String IlluminationMode = "illum";
        public static final String TextureImage = "map_Kd";
    }
}
