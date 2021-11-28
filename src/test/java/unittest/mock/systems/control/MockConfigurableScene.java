package unittest.mock.systems.control;

import tech.fastj.graphics.display.FastJCanvas;

import tech.fastj.systems.control.Scene;

import java.util.UUID;
import java.util.function.BiConsumer;

public class MockConfigurableScene extends Scene {

    private final BiConsumer<FastJCanvas, Scene> load;
    private final BiConsumer<FastJCanvas, Scene> unload;
    private final BiConsumer<FastJCanvas, Scene> update;

    public MockConfigurableScene(BiConsumer<FastJCanvas, Scene> load) {
        this(load, (canvas, scene) -> {}, (canvas, scene) -> {});
    }

    public MockConfigurableScene(BiConsumer<FastJCanvas, Scene> load, BiConsumer<FastJCanvas, Scene> update) {
        this(load, (canvas, scene) -> {}, update);
    }

    public MockConfigurableScene(BiConsumer<FastJCanvas, Scene> load, BiConsumer<FastJCanvas, Scene> unload, BiConsumer<FastJCanvas, Scene> update) {
        super(UUID.randomUUID().toString());
        this.load = load;
        this.unload = unload;
        this.update = update;
    }

    @Override
    public void load(FastJCanvas canvas) {
        load.accept(canvas, this);
    }

    @Override
    public void unload(FastJCanvas canvas) {
        unload.accept(canvas, this);
    }

    @Override
    public void update(FastJCanvas canvas) {
        update.accept(canvas, this);
    }
}
