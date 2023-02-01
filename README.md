<div align="center">

![FastJ Logo][FastJ-Logo]

[![Build Status][Build-SVG]][Build-Action]
[![SonarCloud Code Coverage][SonarCloud-CodeCoverage-SVG]][SonarCloud-CodeCoverage-Report]

[![Javadoc][JavaDoc-SVG]][JavaDoc]
[![Maven Central][Maven-Central-SVG]][Maven-Central]
[![jitpack.io][Jitpack.IO-SVG]][Jitpack.IO]
[![Discord Server][FastJ-Discord-Server-SVG]][FastJ-Discord-Server]
</div>

## What is FastJ?

**FastJ** is a _free_ and open-source Java-based 2D game engine and framework. It aims to provide the best 2D
game-making experience using Java (and the JVM languages).

### Disclaimer

**This project is still under heavy development.** There is a very good chance bugs are still prevalent and untracked,
as the engine is not fully covered by unit tests. Documentation is readily available, but may change over
time. [Feel free to help us out by contributing to the project!](#contributing-to-fastj)

## Projected Feature List

| Feature              | Description                                                                                                                                                                                                                | Support |
|:---------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:-------:|
| 2D Rendering         | Rendering of [Shapes](examples/java/tech/fastj/examples/polygon2d/Main.java), [Models](examples/java/tech/fastj/examples/model2d/Main.java), [Text](examples/java/tech/fastj/examples/text2d/Main.java), Sprites, Lighting |    ✔    |
| Scene System         | [Scene-based Rendering &amp; Scene Switching](examples/java/tech/fastj/examples/scene/Main.java)                                                                                                                           |    ✔    |
| Scriptable Behaviors | [Control GameObject state](examples/java/tech/fastj/examples/behaviors/Main.java)                                                                                                                                          |    ✅    |
| Desktop Support      | Full Compatibility on Windows, Linux, and macOS                                                                                                                                                                            |    ✅    |
| Audio Engine         | [Loading, Playing, and Controlling .wav, .ogg, .mp3 files](examples/java/tech/fastj/examples/audio/Main.java)                                                                                                              |    ✅    |
| Image Support        | Image rendering, Spritesheet Creation                                                                                                                                                                                      |    ✅    |
| Animation Engine     | Interpolation, Sprite Animation, Tweening Framework                                                                                                                                                                        |    ⭕    |
| UI System            | Flexible UI System to replace Swing UI                                                                                                                                                                                     |    ⭕    |
| Particle Engine      | Particle Emitters, Interaction with Game Elements                                                                                                                                                                          |    ⭕    |
| Physics Engine       | Custom 2D Physics Engine                                                                                                                                                                                                   |    ❌    |
| Asset Manager        | Load, Unload, and Hot Reload Game Assets                                                                                                                                                                                   |    ⭕    |
| FastJ Model Creator  | Create & Export 2D Models with an editor                                                                                                                                                                                   |    ⭕    |
| FastJ Editor         | Make Games in FastJ with an editor                                                                                                                                                                                         |    ⭕    |
| Web Support          | Run FastJ in Modern Web Browsers                                                                                                                                                                                           |    ❌    |

✅ is complete and satisfactory.

✔ is implemented and usable, but is missing certain features.

⭕ is in progress.

❌ is not yet implemented.

Features with examples available have their prime example linked.

## How do I Learn FastJ?

There are many ways to learn FastJ -- check them out below!

### Template Projects

Use a template project! It's the fastest way to jump into using FastJ. You just need to pick a programming language from
the ones below. We suggest Java, as it has the most tutorial content, but Kotlin and Groovy are fine options as well.

- [FastJ Java Template Project](https://github.com/fastjengine/fastj-java-template)
- [FastJ Kotlin Template Project](https://github.com/fastjengine/fastj-kotlin-template)
- [FastJ Groovy Template Project](https://github.com/fastjengine/fastj-groovy-template)

### Tutorials

[FastJ provides article tutorials on its website][FastJ-Tutorials] to accommodate as many types of developers as
possible. From beginners to experts, the website tutorials are written to give enough information to satisfy anyone
willing to learn!

### Code Examples

[Explore FastJ's code examples][FastJ-Examples] to see the different ways FastJ can be used, and all of its features.
These come with in-example explanations and easy plug-and-playability to give you the best chance at understanding how
FastJ works.

### API Documentation

[Check out FastJ's documentation][Javadoc] to get a better understanding of the code FastJ provides to improve your
game-making experience.

### I'll add the dependency myself!

Well in that case...

<details>
<summary>
Click here to expand the manual dependency instructions.
</summary>

#### Dependency Management

This library can be found in the following places:

- [jitpack.io][Jitpack.IO], as a dependency.
- [Maven Central][Maven-Central], as a dependency or as a jarfile.
- The [Releases][Releases] section of this repository.

When adding the dependency, **make sure to replace `[latest version here]` with the actual version** (you'll find this
in the jitpack.io or Maven Central link up above). **The current latest version is 1.7.0-SNAPSHOT-2**.

A few common dependencies are provided below:

- **Gradle Build Script**
    - Groovy:
      ```groovy
      repositories.mavenCentral()
  
      dependencies.implementation('io.github.lucasstarsz.fastj:fastj-library:1.7.0-SNAPSHOT-2')
      ```
    - Kotlin:
      ```kotlin
      repositories.maven {
          setUrl("https://jitpack.io")
      }
  
      dependencies.implementation("io.github.lucasstarsz.fastj:fastj-library:1.7.0-SNAPSHOT-2")
      ```
- **Maven POM**
  ```xml
  <dependency>
    <groupId>io.github.lucasstarsz.fastj</groupId>
    <artifactId>fastj-library</artifactId>
    <version>1.7.0-SNAPSHOT-2</version>
  </dependency>
  ```

You'll also want to make sure you add a dependency for a logging framework from [SLF4J][SLF4J], since FastJ uses it for
all its logging purposes. Take your pick!

</details>

## Community

FastJ's community of developers is growing day by day!

- Join the [FastJ Discord Server][FastJ-Discord-Server] to join a growing community of game developers, artists, and
  more! We host [Game Jams](https://itch.io/jam/fastj-game-jam-2022) every few months, so keep an eye out 😄

## Games Made with FastJ

### **[Stack Attack](https://lucas-z.itch.io/stack-attack)** by [lucasstarsz](https://github.com/lucasstarsz)

![Gameplay of Stack Attack by Lucas Z](https://img.itch.zone/aW1nLzg5NTA3MjIuZ2lm/original/FvfecS.gif "Stack Attack")

### **[Unnamed Rhythm Game](https://lucas-z.itch.io/rhythm-game)** by [lucasstarsz](https://github.com/lucasstarsz)

![Gameplay of Rhythm Game by Lucas Z](https://img.itch.zone/aW1hZ2UvMTU3OTU3My85MjI2ODA2LmdpZg==/347x500/Qqcd3w.gif "Unnamed Rhythm Game")

Want your FastJ game featured? [Open an issue](https://github.com/fastjengine/FastJ/issues/new),
or [ask in our discord][FastJ-Discord-Server]!

## Contributing to FastJ

Plan on contributing to the repository? Awesome! We're glad to have you ❤️

Please be sure to read over the [contribution guidelines][Contributing-Guidelines] so that you can get started.

## License

This repository is Free and Open Source, licensed under the [MIT License][MIT-License].

## Dependencies

- [SLF4J][SLF4J] for logging support.
- [JOrbis] and [VorbisSPI] for .ogg support.
- [MP3SPI] for .mp3 support.
- [tritonus-share] for utility processing tools related to above audio libraries.

[FastJ-Logo]: https://raw.githubusercontent.com/fastjengine/FastJ/main/media/branding/fastj_logo.png "FastJ Logo"

[SonarCloud-CodeCoverage-SVG]: https://img.shields.io/sonar/coverage/fastjengine_FastJ?labelColor=363e45&logo=sonarcloud&logoColor=f3702a&server=https%3A%2F%2Fsonarcloud.io&style=for-the-badge

[SonarCloud-CodeCoverage-Report]: https://sonarcloud.io/dashboard?id=fastjengine_FastJ "SonarCloud Code Coverage"

[Build-Action]: https://github.com/fastjengine/FastJ/actions?query=workflow%3ABuild "Build Status"

[Build-SVG]: https://img.shields.io/github/actions/workflow/status/fastjengine/FastJ/build.yml?branch=main?label=Build&labelColor=363e45&style=for-the-badge&logo=data:image/png;base64,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

[Jitpack.IO]: https://jitpack.io/#fastjengine/FastJ "FastJ on jitpack.io"

[Jitpack.IO-SVG]: https://img.shields.io/jitpack/v/github/fastjengine/FastJ?label=jitpack.io&style=for-the-badge&labelColor=363e45&color=blue&logo=jitpack

[FastJ-Discord-Server]: https://discord.gg/w4RaU3AtPe "Join the FastJ Discord Server!"

[FastJ-Discord-Server-SVG]: https://img.shields.io/discord/869808657498513419?style=for-the-badge&labelColor=363e45&&logo=discord

[Maven-Central]: https://maven-badges.herokuapp.com/maven-central/io.github.lucasstarsz.fastj/fastj-library "FastJ on Maven Central"

[Maven-Central-SVG]: https://img.shields.io/maven-central/v/io.github.lucasstarsz.fastj/fastj-library?style=for-the-badge&color=blue&label=Maven%20Central&labelColor=363e45&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAHaklEQVR4XuVZaWwVVRT+zp23FJVFYgQN4NRSWqgUFFuEgEpAJGFxF03UxMSgwQQ1FASLFAnUFovbD+Lyw2A0BgMkCBgx7qAIKMpOW6SDETVuQaKgfTNzzL0z074HJbTMndfFl7SveZ0593zf+b5z7p1H6EQvfi2fG08CyZn1pCttbYF0JXSmOCdezmMwwDbBTQHdHzukJXctQaIGf/KVPFZrMOA6gJsiOCmgV1l4Ejo8AaryAMjPlH0CXFsSAfR6PBwJHZqAAHxAgGLCBdzABgkD9HBtKAyhbo5S+nZNATd2t5uWMATDZS9dWXlpA6mGHrO7oAJ+mZ/PzITuuY4CnBMn/Gt7bUC1At8GcRCSs8JNhA6pgJ/nDmISQLI3I97ThSEA103rA9IGKcL5CQF6sC4UhlA3R2GBH2cVMsUYIs4AAX2q6ujkq3nMLpA6LvDvMW8MSoLkh32ru5AFjs4YzCzBxwCKMxIJRsrxwLb0kuSELULoAGETCO7//p4hzHGoyksFUBzonWPgWKMNCI8EEp4q5Kt3NwPxigOh8w8dQBcBDXcXsQQdEND/5QN09OFCzgCfRkSf6vDVV+NVF4AwcepvGcrC970koFuS4DBgk+vJnwAyOOPvS54NN/+DfNudgKNTruQT5CjZq+rHGblv7qMj9xUxhA9avht+LyBGvxUHteWtLdC5KuDgDcM4kL2IA2w4iBkGOKi+732lBJ+Q/q+E936HUMB3147gRsNJa3wMoRodAb7kVeNL8/5lK/drLZrWYG1Rwbd5JRzrY0NIoHFv9BW8v4vqJherTZD0vAc8sAFwQZJw8co9WnPWGqwtBHx1eYna2ef0tRUBsgdAuBBCeOPOJyG9Ceat3qs9X+0BW0NC3cDRfNxNKaUnLnIgJ0DR59/Q/uuHc1DxdM9LMgZt2B1JrpEEPRsJOy4v9c91rOZwn0uBY7atOn3Q6AICeiUNXPLuTvphbAn327xDe77aA54NvKz+n47tP+Bg9T7iux205+qrGHLUSe/LI+8/BE4JJAwgJVxlieKvdmrPV3vAsxGwPXckk3y2JTc3AGICiLOBk/KMm/a52qXJLbFPyvA9X0eSayRBz0TCNlOCl8g86ctfJYe3047cUvVBczKeMtQlBnBhwkDu3i8jyTWSoGci4EvzGlV9SiPAYan8AG3LJEiLnE1Z5/r/yAKfmtAWcwwbcCDgNvm/tGE7SUso6QcHk2YulCWuPhwd+KwehjabY5sJACMpgOGHt5GyhQKdRkKaPc61sq29LysK+Cx3PINteArwVDDS2kZbzVEs/86svjoB4zzhNcYRh7dHmmOkwYMqfGSOV9UPCJDvkoAvzNGqJ7REgkyspCFa8FmxwCe5k9hhW1XeI8EFsQOH5DNdFwEBqjkGNiCgtGFbVooT+SKbzEncDN6T/1hrM31qXudNBF8BwbtwGRfEYvjLsVF6JHoSIiVgw4CpHBPp1XfQUwg0Io4TbuNp4NPJkBZpbSMLc12ki2wwp/qdv1n+46yP6ANzgu99qQAXQikh0w6jrK2R5haQFuki681pTaNP+r+3EPjFBuTXXGo/4AM/lYAx1pZI80pXTKQLvWPelEHARGsTbTInM+CcBj6dhGutzyLNKysEbMidzg43pnV/Bzda79FGc4qa/c3Sz1TDOOvjrIGPdAyuMe84rfs7SICV51smQH73PcH6sGsQsNq8M4OAKdZ6Wmfe3OLoC3rBROv9rIKPTAFvmXexAXnK87p/ghh/cwyxFjY+QTPMIcK4ho1dg4A1A2ewbR9vIuAWay1JRbS085MEJAiY2LA+6+AjU8Cy/Dk8IPVDEwG3WmvobXM6e50+mP3BGGRMs9a1C/jICKjOL2MzddTf6aXgIMdvfpkEyCPwrdbadgOvlYCa4uVctnu2AlM1aA7H2EH/1E+Ybr1Fb5r3KvkHCpCyv8Na1a7AI9kJLiws58UHl9LTPgEFzm/4wwEMoqadXwLA7dYbCvwLA8v4kUM17UqE1sUrCss5ISvtOpAKmFO/nF437+dGiuGBhleb1nomf7Z6DJYUCcyqrdKaQ1sPRtoXX1wwX8lf/sytX05LCuYpsMTek2CvCQIxEMrqlmlfv90JWFK4gMlNoaeI43f2Or3XbPwGyEB5XTVV5s/hJ+qf6XoELC4sZ8E2FtRWk7SEAq6+B/EqL5/3GSQw7+DSdgevdQrIYOWDF3KcHRjs4MnaKnpSqsGvfpMKGKiorewQ4CMhQHq/h0jiV9eGob7tkQogxEig4sCiDgNc6xicXbSUl+8rzwA3b8girtrf8QCf2iS1V6Ri8EJ+6sBiFXfu4EW8rANWPdIHIuWDFvDSuiXaiW3reGvt9Z0m0dYCaut1WglYcEUlL9n7hNaYbQXU1uu1Jls2ZBHXdILGF2kPaGsF2vt6bQqYP+x5fnrXo9riZYsYbQmXFddwze4ybfE6FQEzh1bxij3zOh14bVvhWcOe4xd3Pfb/JeChomX80r65/18CsuXXKNYJXbXpxZW8anfn2vxo3QfcVlzJazoxAf8BpywUbjJvd/MAAAAASUVORK5CYIIA

[JavaDoc]: https://javadoc.io/doc/io.github.lucasstarsz.fastj/fastj-library "FastJ Documentation"

[JavaDoc-SVG]: https://javadoc.io/badge2/io.github.lucasstarsz.fastj/fastj-library/Javadocs.svg?style=for-the-badge&color=blue&labelColor=363e45&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAFEklEQVR4Xu1bbXLbNhBdgFR6mLoWSSX2AfLVnqYZKW4mR8iHPT1NIjfJTH7Gqi1Rck5TkdjOgoJCyaQIkASoicxf8hDE7j487GIfaQYHfrEDjx/uAbhnwJ4iMOn38WQ+t85Q6wbq4vtvGOGj2dS6f9YN1AVgEkZ4cg/AgTJgEoYIwOBgGUD09xiDwfTG+ha1bsA0B0yCEIExwGQJp7e31v2zbsAYgDBCesYF/cnOXgFA1HcZ/F4BoILnjMFDB3tfMXMvGKCCd736nTNgcnyM4PnrNOFq3+fzUmcMyK961cpfhxEKRDiJZ6372/qEOllfBrQaSA6UnfmvowEiYul9HVtVY5wDkF/5B5xDcHN9x4ebaIApyoJgvRw6BSAfPALC6WyT0t+CADnjTgJ3XgXywTMh4NE83gC/q0rghAEbCa8gmVHvnxHePuW3c4J1APLBlSW8NUCWMv2uRGgdgCpqd7n6Tg5CVQBU3a8qY03vO2NAWX9/MAD4jEFU0OSYnAibrnbR8w4YkMlblQnwZ60CFNeuPj+fBFVSciGHd3IQ0mUBnf9PLTQ+nWwBZfQqjJAAKGp5r4IQGdvcja5aY+s5II860Z3+LqJ4V8nQKQAUPLW4QojC3r6LkugcAAJhGg2wsCSuJHGXPUEnAFCA8eAhbmsB9EYYuAcgEE7m7as/nSZBnUMMCSEkfemWwf7oEmXuRIT4/Pdai1nroV3BBC8vkXs+JMslLC7MnLrqh3haY+UDBQQwmL1/ZhST0eCywKO/PiOKFEjnm79/3sqcOozJj/kBAsDMwIdGzoYvL5WOASkKWJz/0Wi+ncwaXWIKCLc7qK78SYWAxYWeL7UdJqpvP8wYBzJO8s7iQo8JfQIREeiNEFDXwDngWjMGoDkFCog1VjU6+4T0LNmPz/Xs1wYgv1rHwzF6XiZmSuumF2NZoLSNEGFRM6GtGWAwRysAmMZra7wCwFkOsBVInXnDs3/kVqLdE2tuP9V91rFX+kwwGlMzB3PDEtjEiSZVqNUtoJIQneRmDQHoj8bo+T2Yvnm800dl04T2ebDtALBhIUtw9NmLSBOpDKk0Sb8Z94F4K2mzvrPJhzQRsPj7blmjPU8VYt6g/LYKgHI7evUFMU1qs1qWQiFKDzSD11/l/NO3Txr733gC3SiPXoyx5zG5xoIBcGRy1ecGCUvXlsk4ZwCYOOVy7D0ANtBWZaluZrbhU9mcVhiQb5Kk4dxRlwwKpPcE9D0kZYSsShRVASEoR5i11KbgWQGAnFAnM1OHNsYzDrN3T635aOUkuB2wFEi4B6QX7L6Y7ASpIdLp/BoBm3u4FrpSfOBUxgBiQwWmLce35zkafsTvmhpA7ZNgePZJdhuIDOJzM+nJVuBqXtpys3fmPmkzIJ/Y9im7B0PSIDlMa+YKLQD6ZGSld2zQh/uQpMvaAoYpK3598QF7ngfcW+WUkiRJW9SKInQ0+ogP/F+yhJaTre4EwjjQuz5MScVTV/ZrVfiAPpOTxZCOxeoGXz1Hc28rS1RKuQ/L5L/duqDhVtBigM5KHY/GSLqenHBV96W2pyWR0Vkgc4W6O3qGPpS81Uxqvw2pz/AkqKZ5oDUAdEBqa8zRnx+w1/Oz/3ZABMa92p1hawCQEkSr2GaC7A/HyDlRn/Y8vXXItpE6OZq+BClagNYAUJNTH6B+J8sEPI8BU5/EK9EDVSg/pJG1I7QVpEROFZdU4maCRxXrWgegyuC+3f8fARJ0X9/EzrEAAAAASUVORK5CYIIA

[SLF4J]: https://www.slf4j.org/ "Simple Logging Facade for Java"

[Releases]: https://github.com/fastjengine/FastJ/releases/ "FastJ Releases"

[MIT-License]: https://github.com/fastjengine/FastJ/tree/main/LICENSE.txt "MIT Licensing"

[FastJ-Tutorials]: https://fastj.tech/wiki/fastj-basics/fastj-quick-start "FastJ Tutorials"

[FastJ-Examples]: https://github.com/fastjengine/FastJ/tree/main/src/example "FastJ Examples"

[Contributing-Guidelines]: https://github.com/fastjengine/FastJ/tree/main/.github/CONTRIBUTING.md "Contributing to FastJ"

[Terminals Are Different]: https://gist.github.com/lucasstarsz/9bbc306f8655b916367d557043e498ad "Terminals Access Files Differently"

[AdoptOpenJDK-Java11-Link]: https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot "Download Java 11"

[Git-Link]: https://git-scm.com/downloads "Download Git, the powerful source control management tool."

[Gradle-Link]: https://gradle.org/install/ "Download Gradle, the powerful build tool."

