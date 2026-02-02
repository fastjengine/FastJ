package tech.fastj.resources.models;

import tech.fastj.engine.CrashMessages;
import tech.fastj.graphics.Boundary;
import tech.fastj.graphics.game.Model2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.RenderStyle;
import tech.fastj.logging.Log;
import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.resources.files.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * WIP Utility class for reading and writing {@link Polygon2D} to and from the {@code .OBJ} file format.
 *
 * @author Andrew Dey
 * @since 1.6.0
 */
public class ObjUtil {

    private static final String LineSeparator = System.lineSeparator();

    private ObjUtil() {
        throw new java.lang.IllegalStateException();
    }

    public static Polygon2D[] parse(Path modelPath, List<String> lines) {
        List<Polygon2D> polygons = new ArrayList<>();
        List<float[]> vertexes = new ArrayList<>();

        Path materialLibraryPath = null;
        String currentMaterial = "";

        for (String line : lines) {
            String[] tokens = line.split("\\s+");

            switch (tokens[0]) {
                case ParsingKeys.Vertex -> vertexes.add(parseVertex(tokens));
                case ParsingKeys.ObjectFace -> {
                    Pointf[] vertexesFromFaces = parseVertexesFromFaces(vertexes, tokens);
                    Polygon2D polygonFromVertexes = Polygon2D.fromPoints(vertexesFromFaces);

                    MtlUtil.parse(polygonFromVertexes, materialLibraryPath, currentMaterial, true);
                    polygons.add(polygonFromVertexes);
                }
                case ParsingKeys.ObjectLine -> {
                    boolean isLastOutline = false;
                    int lastPolygonIndex = polygons.size() - 1;
                    Pointf[] lastVertexes = polygons.get(lastPolygonIndex).getPoints();
                    Pointf[] vertexesFromFaces = new Pointf[tokens.length - 1];

                    for (int j = 0; j < tokens.length - 1; j++) {
                        int vertexesIndex = Integer.parseInt(tokens[j + 1].split("/")[0]);

                        vertexesFromFaces[j] = new Pointf(
                            vertexes.get(vertexesIndex - 1)[0],
                            vertexes.get(vertexesIndex - 1)[1]
                        );

                        isLastOutline = lastVertexes[j].equals(vertexesFromFaces[j]);
                    }

                    if (isLastOutline) {
                        polygons.get(lastPolygonIndex).setRenderStyle(RenderStyle.FillAndOutline);
                        MtlUtil.parse(polygons.get(lastPolygonIndex), materialLibraryPath, currentMaterial, false);
                    } else {
                        Polygon2D polygonFromVertexes = Polygon2D.create(vertexesFromFaces).withRenderStyle(RenderStyle.Outline).build();
                        MtlUtil.parse(polygonFromVertexes, materialLibraryPath, currentMaterial, false);
                        polygons.add(polygonFromVertexes);
                    }
                }
                // filenames and paths in .obj files cannot contain spaces, allowing us to use a
                // non-robust solution.
                case ParsingKeys.MaterialLib -> materialLibraryPath = Path.of(modelPath.toString().substring(
                        0,
                        modelPath.toString().indexOf(modelPath.getFileName().toString())
                    ) + tokens[1]
                );
                // material names in .obj files cannot contain spaces, allowing us to use a
                // non-robust solution.
                case ParsingKeys.UseMaterial -> currentMaterial = tokens[1];
                // unused
                case ParsingKeys.VertexTexture, ParsingKeys.ObjectName, ParsingKeys.Empty -> {
                }
                default -> Log.warn(ObjUtil.class, "Unrecognized parsing key: \"{}\"", tokens[0]);
            }
        }

        return polygons.toArray(new Polygon2D[0]);
    }

    private static Pointf[] parseVertexesFromFaces(List<float[]> vertexes, String[] tokens) {
        Pointf[] vertexesFromFaces = new Pointf[tokens.length - 1];

        for (int j = 1; j < tokens.length; j++) {
            int vertexesIndex = Integer.parseInt(tokens[Math.min(j, tokens.length - 1)].split("/")[0]);

            vertexesFromFaces[j - 1] = new Pointf(
                vertexes.get(vertexesIndex - 1)[0],
                vertexes.get(vertexesIndex - 1)[1]
            );
        }

        return vertexesFromFaces;
    }

    private static float[] parseVertex(String[] tokens) {
        return new float[] {
            Float.parseFloat(tokens[1]),
            Float.parseFloat(tokens[2]),
            Float.parseFloat(tokens[3])
        };
    }

    public static void write(Path destinationPath, Model2D model) {
        StringBuilder fileContents = new StringBuilder();

        Path destinationPathNoSpaces = Path.of(destinationPath.toString().replace(' ', '_'));
        int extensionIndex = destinationPathNoSpaces.toString().lastIndexOf(FileUtil.getFileExtension(destinationPathNoSpaces));

        Path materialPath = Path.of(destinationPathNoSpaces.toString().substring(0, extensionIndex) + "mtl");
        String materialPathString = materialPath.toString();
        Path shortMaterialPath = Path.of(materialPathString.substring(materialPathString.lastIndexOf(File.separator) + 1));

        writeMaterialLib(fileContents, shortMaterialPath);

        for (int i = 0; i < model.getPolygons().length; i++) {
            writeVertexes(fileContents, model.getPolygons()[i], i);
        }

        fileContents.append(LineSeparator);

        for (int i = 0; i < model.getPolygons().length; i++) {
            writeVertexTextures(fileContents, model.getPolygons()[i]);
        }

        fileContents.append(LineSeparator);

        for (int i = 0, vertexCount = 0; i < model.getPolygons().length; i++) {
            Polygon2D polygon = model.getPolygons()[i];

            writeObject(fileContents, i + 1);
            writeMaterial(fileContents, polygon, i + 1, vertexCount);

            vertexCount += polygon.getOriginalPoints().length;
        }

        try {
            Files.writeString(destinationPath, fileContents, StandardCharsets.US_ASCII);
        } catch (IOException exception) {
            throw new IllegalStateException(
                CrashMessages.theGameCrashed("a ." + SupportedModelFormats.Obj + " file writing error."),
                exception
            );
        }

        MtlUtil.write(materialPath, model);
    }

    private static void writeMaterialLib(StringBuilder fileContents, Path materialPath) {
        fileContents.append(ParsingKeys.MaterialLib)
            .append(' ')
            .append(materialPath.toString())
            .append(LineSeparator)
            .append(LineSeparator);
    }

    private static void writeVertexes(StringBuilder fileContents, Polygon2D polygon, int polygonIndex) {
        float vertexSpace = polygonIndex / 1000f;
        Pointf[] polygonPoints = polygon.getPoints();

        for (Pointf vertex : polygonPoints) {
            fileContents.append(ParsingKeys.Vertex)
                .append(' ')
                .append(String.format("%4f", vertex.x))
                .append(' ')
                .append(String.format("%4f", vertex.y))
                .append(' ')
                .append(String.format("%4f", vertexSpace))
                .append(LineSeparator);
        }
    }

    private static void writeVertexTextures(StringBuilder fileContents, Polygon2D polygon) {
        Pointf space = Pointf.subtract(polygon.getBound(Boundary.BottomRight), polygon.getBound(Boundary.TopLeft));
        Pointf topLeft = polygon.getBound(Boundary.TopLeft);
        Pointf[] polygonPoints = polygon.getPoints();

        for (Pointf polygonPoint : polygonPoints) {
            float circleX = Maths.normalize(polygonPoint.x - topLeft.x, 0f, space.x);
            float circleY = Maths.normalize(polygonPoint.y - topLeft.y, 0f, space.y);
            fileContents.append(ParsingKeys.VertexTexture)
                .append(' ')
                .append(String.format("%4f", circleX))
                .append(' ')
                .append(String.format("%4f", circleY))
                .append(LineSeparator);
        }
    }

    private static void writeObject(StringBuilder fileContents, int polygonIndex) {
        fileContents.append(ParsingKeys.ObjectName)
            .append(' ')
            .append("Polygon2D_")
            .append(polygonIndex)
            .append(LineSeparator);
    }

    private static void writeMaterial(StringBuilder fileContents, Polygon2D polygon, int polygonIndex, int vertexCount) {
        switch (polygon.getRenderStyle()) {
            case Fill -> {
                writeFillMaterialUsage(fileContents, polygonIndex);
                writeFaces(fileContents, polygon, vertexCount);
            }
            case Outline -> {
                writeOutlineMaterialUsage(fileContents, polygonIndex);
                writeLines(fileContents, polygon, vertexCount);
            }
            case FillAndOutline -> {
                writeFillMaterialUsage(fileContents, polygonIndex);
                writeFaces(fileContents, polygon, vertexCount);
                writeOutlineMaterialUsage(fileContents, polygonIndex);
                writeLines(fileContents, polygon, vertexCount);
            }
        }

        fileContents.append(LineSeparator);
    }

    private static void writeFillMaterialUsage(StringBuilder fileContents, int polygonIndex) {
        fileContents.append(ParsingKeys.UseMaterial)
            .append(' ')
            .append("Polygon2D_material_fill_")
            .append(polygonIndex)
            .append(LineSeparator);
    }

    private static void writeOutlineMaterialUsage(StringBuilder fileContents, int polygonIndex) {
        fileContents.append(ParsingKeys.UseMaterial)
            .append(' ')
            .append("Polygon2D_material_outline_")
            .append(polygonIndex)
            .append(LineSeparator);
    }

    private static void writeFaces(StringBuilder fileContents, Polygon2D polygon, int vertexCount) {
        fileContents.append(ParsingKeys.ObjectFace);
        Pointf[] polygonPoints = polygon.getPoints();

        for (int i = 1; i <= polygonPoints.length; i++) {
            fileContents.append(' ')
                .append(vertexCount + i)
                .append('/')
                .append(vertexCount + i);
        }

        fileContents.append(LineSeparator);
    }

    private static void writeLines(StringBuilder fileContents, Polygon2D polygon, int vertexCount) {
        fileContents.append(ParsingKeys.ObjectLine);
        Pointf[] polygonPoints = polygon.getPoints();

        for (int i = 1; i <= polygonPoints.length; i++) {
            fileContents.append(' ').append(vertexCount + i);
        }

        fileContents.append(LineSeparator);
    }

    public static class ParsingKeys {
        private ParsingKeys() {
            throw new java.lang.IllegalStateException();
        }

        public static final String Empty = "";
        public static final String MaterialLib = "mtllib";
        public static final String Vertex = "v";
        public static final String VertexTexture = "vt";
        public static final String ObjectName = "g";
        public static final String UseMaterial = "usemtl";
        public static final String ObjectFace = "f";
        public static final String ObjectLine = "l";
    }
}
