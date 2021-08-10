module fastj.library.test {
    requires fastj.library;
    requires org.junit.jupiter.api;

    opens unittest.testcases.engine to org.junit.platform.commons;

    opens unittest.testcases.graphics to org.junit.platform.commons;
    opens unittest.testcases.graphics.display to org.junit.platform.commons;
    opens unittest.testcases.graphics.game to org.junit.platform.commons;
    opens unittest.testcases.graphics.gradients to org.junit.platform.commons;
    opens unittest.testcases.graphics.io to org.junit.platform.commons;
    opens unittest.testcases.graphics.util to org.junit.platform.commons;

    opens unittest.testcases.input.keyboard to org.junit.platform.commons;

    opens unittest.testcases.math to org.junit.platform.commons;

    opens unittest.testcases.systems.audio to org.junit.platform.commons;
    opens unittest.testcases.systems.control to org.junit.platform.commons;
}
