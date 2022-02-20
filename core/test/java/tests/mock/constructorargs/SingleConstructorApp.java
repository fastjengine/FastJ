package tests.mock.constructorargs;

import tech.fastj.App;

public class SingleConstructorApp extends App {

    public final String arg1, arg2;
    public final int arg3;

    // TODO: add primitive support
    public SingleConstructorApp(String arg1, String arg2, Integer arg3) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }
}
