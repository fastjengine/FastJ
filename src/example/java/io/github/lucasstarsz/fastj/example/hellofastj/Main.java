package io.github.lucasstarsz.fastj.example.hellofastj;

import io.github.lucasstarsz.fastj.engine.FastJEngine;

public class Main {
    public static void main(String[] args) {
        // Initialize the FastJ Engine with a GameManager instance
        FastJEngine.init("Hello, FastJ!", new GameManager());
        FastJEngine.run(); // Run the game engine
    }
}
