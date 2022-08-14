# Contributing Code

I've no clue how I managed to spend 3 years learning software development without using a proper IDE for developing
larger projects. From the first days of FastJ I quickly realized I needed a rule set for how my code should be written.

## IDE

I absolutely prefer that you use [IntelliJ IDEA][IntelliJ-Link] while developing FastJ, but [Eclipse][Eclipse-Link] is
fairly capable of keeping up as well. VSCode, _I'm personally not a fan of it_ -- it's given me a lot of trouble for
Java development in the past, and I don't plan on using it for bigger Java projects any time soon. However, as long as
you can get the job done in whatever you're comfortable with you're free to use it.

## Spotless

[diffplug/spotless](https://github.com/diffplug/spotless) handles all import styling, and a little bit of general code
formatting as well.

**Remember to run `gradlew spotlessApply` on your code**. On pull requests, the GitHub Action scripts will all check
whether the code is formatted according to `spotless`' liking. If it is not formatted, the checks will fail. Please keep
this in mind!

## General Code Style

For the most part, you can follow along with this codebase using [Google's Java Style Guide][Style-Guide-Link]. It
covers most of the bases, with exceptions as follows:

### Column Limit

#### Java, Kotlin, Groovy

- Character limit of **140**
- Examples:

 ```java
// bad -- too long
/* This class contains the methods needed to initialize and run a game using the FastJ Game Engine. With this, you'll have access to the engine's features in their full force. */

// good
/*
 * This class contains the methods needed to initialize and run a game using the FastJ Game Engine. With this, you'll have access to the
 * engine's features in their full force.
 */

// bad -- too long
public class ClassWithAnAbsurdAmountOfGenerics<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, AB, CD, EF, GH, IJ, KL, MN, OP, QR, ST, UV, WX, YZ> {
}

// good
public class ClassWithAnAbsurdAmountOfGenerics
    <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, AB, CD, EF, GH, IJ, KL, MN, OP, QR, ST, UV, WX, YZ> {
}

// bad -- chain statement with bad formatting
EngineConfig engineConfig = EngineConfig.create().withTargetFPS(fps).withTargetUPS(ups).withWindowResolution(windowResolution).withCanvasResolution(canvasResolution).withHardwareAcceleration(hardwareAcceleration).withExceptionAction(exceptionAction).build();

// good
EngineConfig engineConfig = EngineConfig.create()
    .withTargetFPS(fps)
    .withTargetUPS(ups)
    .withWindowResolution(windowResolution)
    .withCanvasResolution(canvasResolution)
    .withHardwareAcceleration(hardwareAcceleration)
    .withExceptionAction(exceptionAction)
    .build();

// good -- single-line docs are okay with correct length
/** The main control hub of the game engine. */
```

- Exceptions: JUnit Test Programs

#### Markdown, Build Scripts, Everything Else

I don't care about the exact style of these in terms of this, but lines should hard-wrap at a **120-character** 
limit, and not at 100.

### Block Indentation

Indentation must be **4 spaces**. Indentations for method chaining must be **8 spaces**.

### Wildcard Imports

Wildcard imports are only allowed if the file's import count from that specific package is more than 10, with the same
situation being applied to static imports more than 7.

### Annotations

- Single parameterless annotations for a field are always welcome to be on the same line.
- Otherwise, annotations must always be on a line of its own (one annotation per line).

### Constant Names

All constant values (`static final`, `enum`) should follow `PascalCase`, rather than `CONSTANT_CASE`.
Furthermore, shallow constants (e.g. `static final Set<Integer>`) should _also_ use `PascalCase`.

### Type Variable Names

No prefixes or suffixes. I have no preference on how these are named, other than that you should name variables
_sensibly_.

### Acronyms in Variable Names

So long as they are only used when it makes the most sense to (e.g. FPS, UPS, or GL in the context of OpenGL), they are
preferred. If it is not something easily recognized by acronym, it should not be given one (e.g. FJGL -- FastJ Game
Library).

## Code Comments

If the code calls for it, please do provide some. It may also be a sign that your code could otherwise be simplified 😛

- Comments, wither single or multi-block, should remain within 100 characters long. The rest should be on a new line.
- If a comment is found to be unhelpful or otherwise redundant in a PR, the PR may not be allowed to merge until the
  usage of those comments is discussed thoroughly.

## Documentation

Please see [Contributing Documentation][Contributing-Documentation].

## Logging

FastJ uses [SimpleLogger](https://www.slf4j.org/api/org/slf4j/impl/SimpleLogger.html) as its supplementary logging
library to `SLF4J`. As such, the `test` section contains a `simpleLogger.properties` file that can be edited -- but
preferably not updated -- over time.

- Use `Log.`, and not `FastJEngine.` in your logging -- each logging statement should specify which class it comes from.
- Use message parameterization --
  review [this section of SLF4J's documentation](https://www.slf4j.org/faq.html#logging_performance) for more details.

An example of the expected logging scheme is provided below:

```java
Log.warn(MyClass,"message {}",formattedContent);
```

## Unit Tests

Unit Tests are an essential part of the project's success. No part of the project should be left untested -- I am
currently working hard to give this project as much unit testing as possible.

New code added to the repository should **always** be accompanied by unit testing. There is no exception to this -- PRs
without unit tests for new methods added (of course, there are exceptions to this rule) will **not** be merged.

FastJ makes use of [JUnit 5](https://junit.org/junit5/) for its unit testing purposes. If you're not directly familiar
with this version of JUnit (as it has some key differences from its predecessor JUnit 4) please make sure you read
through the JUnit guide before/while you write your unit tests.

- [Webpage/Docs version of the guide](https://junit.org/junit5/docs/current/user-guide/)
- [PDF version of the guide](https://junit.org/junit5/docs/current/user-guide/junit-user-guide-5.8.2.pdf)

### Visibility

- All unit test classes and test cases should have `package-private` visiblity.
- All other components to the unit tests should be given visibility based on best judgement.
- All packages in `unittest` that contain classes with test cases should be opened to `junit-platform-commons` in
  the `module-info.java` file, for example:
    ```java
    // inside module-info.java
    opens unittest.testcases.graphics to org.junit.platform.commons;
    ```

### Naming

- Classes should contain test cases regarding the topic being tested against.
- For checking normal assertions:
    ```java
    @Test
    void checkSomeThingAction<_withOtherRelatedInformation><_shouldPerformAction>() {}
    
    @Test
    void checkSomeThings_SpecificInformation() {}
    ```
- For exception-related assertions:
    ```java
    @Test
    void tryErroneousAction<_withInvalidOrErroneousParameters>_shouldThrowExceptionOrSomeOtherAction() {}
    ```
- For `BeforeAll` assumptions:
    ```java
    @BeforeAll
    public static void onlyRunIfSomeCondition() {}
    ```

Items in `<>` are optional.


[IntelliJ-Link]: https://www.jetbrains.com/idea/ "IntelliJ IDEA IDE"

[Eclipse-Link]: https://www.eclipse.org/downloads/ "Eclipse IDE"

[Style-Guide-Link]: https://google.github.io/styleguide/javaguide.html "Google Java Style Guide"

[Contributing-Documentation]: contributing-documentation.md "Contributing Documentation"
