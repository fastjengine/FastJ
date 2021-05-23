package io.github.lucasstarsz.fastj.example.hellofastj;

import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.graphics.Display;

import io.github.lucasstarsz.fastj.systems.control.SimpleManager;

public class HelloFastJ extends SimpleManager {

    @Override
    public void init(Display display) {
    }

    @Override
    public void update(Display display) {
    }

    public static void main(String[] args) {
        FastJEngine.init("Hello, FastJ!", new HelloFastJ());
        FastJEngine.run();
    }
}
