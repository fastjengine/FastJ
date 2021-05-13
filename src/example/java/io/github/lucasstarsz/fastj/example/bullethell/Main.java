package io.github.lucasstarsz.fastj.example.bullethell;

import io.github.lucasstarsz.fastj.engine.FastJEngine;

public class Main {
    public static void main(String[] args) {
        FastJEngine.init("Simple Bullet Hell", new GameManager());
        FastJEngine.setTargetFPS(120);
        FastJEngine.run();
    }
}
