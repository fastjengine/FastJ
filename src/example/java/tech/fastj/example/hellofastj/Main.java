package tech.fastj.example.hellofastj;

import tech.fastj.engine.FastJEngine;
import tech.fastj.graphics.Display;

import tech.fastj.systems.control.SimpleManager;

public class Main extends SimpleManager {

    @Override
    public void init(Display display) {
    }

    @Override
    public void update(Display display) {
    }

    public static void main(String[] args) {
        FastJEngine.init("Hello, FastJ!", new Main());
        FastJEngine.run();
    }
}
