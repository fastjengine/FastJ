package tests.mock.constructorargs;

import tech.fastj.App;

public class MultiConstructorApp extends App {

    public static final String DefaultArgValue = "";

    public final String arg1, arg2, arg3;

    public MultiConstructorApp() {
        this.arg1 = DefaultArgValue;
        this.arg2 = DefaultArgValue;
        this.arg3 = DefaultArgValue;
    }

    public MultiConstructorApp(String arg1) {
        this.arg1 = arg1;
        this.arg2 = DefaultArgValue;
        this.arg3 = DefaultArgValue;
    }

    public MultiConstructorApp(String arg1, String arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = DefaultArgValue;
    }

    public MultiConstructorApp(String arg1, String arg2, String arg3) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }
}
