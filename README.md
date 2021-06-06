<div align="center">

![FastJ Logo][FastJ-Logo]

[![Windows Build Status][Windows-Build-SVG]][Windows-Build-Action]
[![Ubuntu Build Status][Ubuntu-Build-SVG]][Ubuntu-Build-Action]
[![Mac OS Build Status][MacOS-Build-SVG]][MacOS-Build-Action]

[![Javadoc][JavaDoc-SVG]][JavaDoc]
[![Maven Central][Maven-Central-SVG]][Maven-Central]
[![SonarCloud Code Coverage][SonarCloud-CodeCoverage-SVG]][SonarCloud-CodeCoverage-Report]
</div>

## What is FastJ?
FastJ is an open-source, Java-based 2D game engine and framework. Originally named the [FastJ Engine][FastJ-Engine-Link], it aims to provide an easy-to-use, 2D game-making library.

## Disclaimer
**This project is still under heavy development.** There is a very good chance bugs are still prevalent and untracked, as the engine is not fully covered by unit tests. Documentation is readily available, but may change over time. [If you would like to help out, all help is appreciated!](#contributing-to-fastj)


## Projected Feature List

|       Feature        |                         Description                         | Support |
| :------------------- | :---------------------------------------------------------- | :-----: |
| 2D Rendering         | Rendering of 2D Polygons, Models, Lighting                  |    ✔    |
| Scene System         | Scene-based Rendering, Scene Switching                      |    ✔    |
| Scriptable Behaviors | Control game object states through behaviors.               |    ✅    |
| Desktop Support      | Full Compatibility on Windows, Linux, and macOS.            |    ✅    |
| Audio Engine         | Play sounds of several different formats at any given time. |    ✔    |
| Image Support        | Image rendering, transformations, and sprite animation.     |    ❌    |
| Animation Support    | Interpolation and tweening framework.                       |    ❌    |
| UI System            | Flexible UI system to replace the need for Swing UI.        |    ⭕    |
| FastJ Model Creator  | Create & Export 2D Models quickly and easily.               |    ❌    |
| FastJ Editor         | View, Run, Build, and Export FastJ games from an editor.    |    ❌    |
| Web Support          | Compile FastJ games to run in a browser.                    |    ❌    |

✅ is complete and satisfactory.

✔ is implemented and usable, but is missing certain features.

⭕ is in progress.

❌ is not yet implemented.


## Adding FastJ to your project
This library can be found in the following places:
- [Maven Central][Maven-Central], as a dependency or as a jarfile.
- The [Releases][Releases] section of this repository.

### Adding the Dependency
When adding the dependency, **make sure to replace `[latest version here]` with the actual latest version** (you'll find this in the Maven Central link up above). **The current latest version is 1.4.0**.

A few common dependencies are provided below:

- **Gradle**
    - Groovy:
      ```groovy
      implementation 'io.github.lucasstarsz.fastj:fastj-library:[latest version here]'
      ```
    - Kotlin:
      ```kotlin
      implementation("io.github.lucasstarsz.fastj:fastj-library:[latest version here]")
      ```
- **Maven**
  ```xml
  <dependency>
    <groupId>io.github.lucasstarsz.fastj</groupId>
    <artifactId>fastj-library</artifactId>
    <version>[latest version here]</version>
  </dependency>
  ```
- **Apache Ivy**
  ```xml
  <dependency org="io.github.lucasstarsz.fastj" name="fastj-library" rev="[latest version here]" />
  ```


## Learning FastJ

There will likely be a small wiki added for this game engine at a later time. For now, you'll want to check out [the example projects][FastJ-Example] for getting started with FastJ.

From there, explore the example code, play around with it, and read the [API documentation][Javadoc] to get a better understanding of FastJ.

## Contributing to FastJ
Plan on contributing to the repository? Great! Be sure to read over the [contribution guidelines][Contributing-Guidelines].

### Building FastJ
You'll need a few things in order to work on the repository:
- [Git][Git-Link]
- [Java 11][AdoptOpenJDK-Java11-Link]
- (optional, but highly recommended!) A decent understanding of how to use [Gradle][Gradle-Link].
  **Installation of Gradle is not required -- the project supplies the Gradle tools already.**
    - For reference, this project currently makes use of Gradle 6.7.1.

Once you have what you need, follow these simple steps:
- Clone the FastJ repository.
  ```bash
  git clone https://github.com/lucasstarsz/FastJ.git
  ```
- Build the project.
  ```bash
  ./gradlew build
  ```
  _Having trouble using `gradlew`? Read [this][Terminals Are Different]._


## License
This repository is licensed under the [MIT License][MIT-License].


[FastJ-Logo]: .github/media/fastj_logo.png "FastJ Logo"

[SonarCloud-CodeCoverage-SVG]: https://img.shields.io/sonar/coverage/fastjengine_FastJ?labelColor=363e45&logo=sonarcloud&logoColor=f3702a&server=https%3A%2F%2Fsonarcloud.io&style=for-the-badge
[SonarCloud-CodeCoverage-Report]: https://sonarcloud.io/dashboard?id=fastjengine_FastJ "SonarCloud Code Coverage"

[Windows-Build-Action]: https://github.com/lucasstarsz/FastJ/actions?query=workflow%3ABuild-Windows "Windows Build Status"
[Windows-Build-SVG]: https://img.shields.io/github/workflow/status/lucasstarsz/FastJ/Build-Windows?label=Windows%20Build&labelColor=363e45&logo=windows&logoColor=0078D6&style=for-the-badge

[Ubuntu-Build-Action]: https://github.com/lucasstarsz/FastJ/actions?query=workflow%3ABuild-Ubuntu "Ubuntu Build Status"
[Ubuntu-Build-SVG]: https://img.shields.io/github/workflow/status/lucasstarsz/FastJ/Build-Ubuntu?label=Ubuntu%20Build&labelColor=363e45&logo=ubuntu&logoColor=E95420&style=for-the-badge

[MacOS-Build-Action]: https://github.com/lucasstarsz/FastJ/actions?query=workflow%3ABuild-MacOS "Mac OS Build Status"
[MacOS-Build-SVG]: https://img.shields.io/github/workflow/status/lucasstarsz/FastJ/Build-MacOS?label=Mac%20OS%20Build&labelColor=363e45&logo=apple&logoColor=000000&style=for-the-badge

[Maven-Central]: https://maven-badges.herokuapp.com/maven-central/io.github.lucasstarsz.fastj/fastj-library "FastJ on Maven Central"
[Maven-Central-SVG]: https://img.shields.io/maven-central/v/io.github.lucasstarsz.fastj/fastj-library?style=for-the-badge&color=blue&label=Maven%20Central&labelColor=363e45&logo=data:image/png;base64,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

[JavaDoc]: https://javadoc.io/doc/io.github.lucasstarsz.fastj/fastj-library "FastJ Documentation"
[JavaDoc-SVG]: https://javadoc.io/badge2/io.github.lucasstarsz.fastj/fastj-library/Javadocs.svg?style=for-the-badge&color=blue&labelColor=363e45&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAFEklEQVR4Xu1bbXLbNhBdgFR6mLoWSSX2AfLVnqYZKW4mR8iHPT1NIjfJTH7Gqi1Rck5TkdjOgoJCyaQIkASoicxf8hDE7j487GIfaQYHfrEDjx/uAbhnwJ4iMOn38WQ+t85Q6wbq4vtvGOGj2dS6f9YN1AVgEkZ4cg/AgTJgEoYIwOBgGUD09xiDwfTG+ha1bsA0B0yCEIExwGQJp7e31v2zbsAYgDBCesYF/cnOXgFA1HcZ/F4BoILnjMFDB3tfMXMvGKCCd736nTNgcnyM4PnrNOFq3+fzUmcMyK961cpfhxEKRDiJZ6372/qEOllfBrQaSA6UnfmvowEiYul9HVtVY5wDkF/5B5xDcHN9x4ebaIApyoJgvRw6BSAfPALC6WyT0t+CADnjTgJ3XgXywTMh4NE83gC/q0rghAEbCa8gmVHvnxHePuW3c4J1APLBlSW8NUCWMv2uRGgdgCpqd7n6Tg5CVQBU3a8qY03vO2NAWX9/MAD4jEFU0OSYnAibrnbR8w4YkMlblQnwZ60CFNeuPj+fBFVSciGHd3IQ0mUBnf9PLTQ+nWwBZfQqjJAAKGp5r4IQGdvcja5aY+s5II860Z3+LqJ4V8nQKQAUPLW4QojC3r6LkugcAAJhGg2wsCSuJHGXPUEnAFCA8eAhbmsB9EYYuAcgEE7m7as/nSZBnUMMCSEkfemWwf7oEmXuRIT4/Pdai1nroV3BBC8vkXs+JMslLC7MnLrqh3haY+UDBQQwmL1/ZhST0eCywKO/PiOKFEjnm79/3sqcOozJj/kBAsDMwIdGzoYvL5WOASkKWJz/0Wi+ncwaXWIKCLc7qK78SYWAxYWeL7UdJqpvP8wYBzJO8s7iQo8JfQIREeiNEFDXwDngWjMGoDkFCog1VjU6+4T0LNmPz/Xs1wYgv1rHwzF6XiZmSuumF2NZoLSNEGFRM6GtGWAwRysAmMZra7wCwFkOsBVInXnDs3/kVqLdE2tuP9V91rFX+kwwGlMzB3PDEtjEiSZVqNUtoJIQneRmDQHoj8bo+T2Yvnm800dl04T2ebDtALBhIUtw9NmLSBOpDKk0Sb8Z94F4K2mzvrPJhzQRsPj7blmjPU8VYt6g/LYKgHI7evUFMU1qs1qWQiFKDzSD11/l/NO3Txr733gC3SiPXoyx5zG5xoIBcGRy1ecGCUvXlsk4ZwCYOOVy7D0ANtBWZaluZrbhU9mcVhiQb5Kk4dxRlwwKpPcE9D0kZYSsShRVASEoR5i11KbgWQGAnFAnM1OHNsYzDrN3T635aOUkuB2wFEi4B6QX7L6Y7ASpIdLp/BoBm3u4FrpSfOBUxgBiQwWmLce35zkafsTvmhpA7ZNgePZJdhuIDOJzM+nJVuBqXtpys3fmPmkzIJ/Y9im7B0PSIDlMa+YKLQD6ZGSld2zQh/uQpMvaAoYpK3598QF7ngfcW+WUkiRJW9SKInQ0+ogP/F+yhJaTre4EwjjQuz5MScVTV/ZrVfiAPpOTxZCOxeoGXz1Hc28rS1RKuQ/L5L/duqDhVtBigM5KHY/GSLqenHBV96W2pyWR0Vkgc4W6O3qGPpS81Uxqvw2pz/AkqKZ5oDUAdEBqa8zRnx+w1/Oz/3ZABMa92p1hawCQEkSr2GaC7A/HyDlRn/Y8vXXItpE6OZq+BClagNYAUJNTH6B+J8sEPI8BU5/EK9EDVSg/pJG1I7QVpEROFZdU4maCRxXrWgegyuC+3f8fARJ0X9/EzrEAAAAASUVORK5CYIIA

[Releases]: https://github.com/lucasstarsz/FastJ/releases/ "FastJ Releases"

[FastJ-Engine-Link]: https://github.com/lucasstarsz/FastJ-Engine "The original: FastJ Game Engine"

[MIT-License]: LICENSE.txt "MIT Licensing"

[FastJ-Example]: src/example "FastJ: Example Game"

[Contributing-Guidelines]: /.github/CONTRIBUTING.md "Contributing to FastJ"

[Terminals Are Different]: https://gist.github.com/lucasstarsz/9bbc306f8655b916367d557043e498ad "Terminals Access Files Differently"

[AdoptOpenJDK-Java11-Link]: https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot "Download Java 11"
[Git-Link]: https://git-scm.com/downloads "Download Git, the powerful source control management tool."
[Gradle-Link]: https://gradle.org/install/ "Download Gradle, the powerful build tool."
