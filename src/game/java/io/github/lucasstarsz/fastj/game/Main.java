package io.github.lucasstarsz.fastj.game;

import io.github.lucasstarsz.fastj.engine.FastJEngine;

public class Main {
    public static void main(String[] args) {
        FastJEngine.init("New Game", new GameManager());
        FastJEngine.run();
    }
}
