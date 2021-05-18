module fastj.library.test {
    requires fastj.library;
    requires org.junit.jupiter.api;

    opens unittest.testcases.graphics to org.junit.platform.commons;
    opens unittest.testcases.graphics.game to org.junit.platform.commons;

    opens unittest.testcases.math to org.junit.platform.commons;
}
