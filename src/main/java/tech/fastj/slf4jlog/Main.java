package tech.fastj.slf4jlog;

import tech.fastj.engine.FastJEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        FastJEngine.log("Hi This is my first SLF4J program");
        FastJEngine.warning("Hi This is my first SLF4J program");
        FastJEngine.error("Hi This is my first SLF4J program", new RuntimeException());
    }
}
