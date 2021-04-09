module fastj.library.test {
    requires fastj.library;
    requires org.junit.jupiter.api;

    exports unittest.testcases.math;

    exports unittest.testcases.graphics;
    exports unittest.testcases.graphics.shapes;
}
