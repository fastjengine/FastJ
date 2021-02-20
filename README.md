# FastJ Game Library
[![Windows Build Status][Windows-Build-SVG]][Windows-Build-Action]
[![Ubuntu Build Status][Ubuntu-Build-SVG]][Ubuntu-Build-Action]
[![Mac OS Build Status][MacOS-Build-SVG]][MacOS-Build-Action]

[![Javadoc][JavaDoc-SVG]][JavaDoc]
[![Maven Central][Maven-Central-SVG]][Maven-Central]

FastJ is a Java-based game engine + framework coming together to make an easily-accessible, 
easily-usable, game making library.

The original version of this library can be found [here][fastj-engine link].

This repository is licensed under the [MIT License][MIT-License].


## Adding FastJ to your project
This ECS can be found on [Maven Central][Maven-Central].

### Adding the Dependency
When adding the dependency, **make sure to replace `[latest version here]` with the actual 
latest version** (you'll find this in the Maven Central link up above).

A few common dependencies are provided below:

- **Maven**
  ```xml
  <dependency>
    <groupId>io.github.lucasstarsz.fastj</groupId>
    <artifactId>fastj-library</artifactId>
    <version>[latest version here]</version>
  </dependency>
  ```
- **Gradle**
    - Groovy:
      ```groovy
      implementation 'io.github.lucasstarsz.fastj:fastj-library:[latest version here]'
      ```
    - Kotlin:
      ```kotlin
      implementation("io.github.lucasstarsz.fastj:fastj-library:[latest version here]")
      ```
- **Apache Ivy**
  ```xml
  <dependency org="io.github.lucasstarsz.fastj" name="fastj-library" rev="[latest version here]" />
  ```

### Using FastJ
FastJ requires Java 11 or later to run.

There will likely be a small wiki added for this game engine at a later time. For now, you'll want 
to check out [this example code][FastJ-Example] for getting started with FastJ.

As for seeing that example in action, you'll need to clone the repository and type the following:
```bash
./gradlew example
```
_Having trouble using `gradlew`? Read [this][Terminals Are Different]._

From there, explore the example code, play around with it, and read the documentation to get a better
understanding of FastJ.


## Building FastJ for yourself
Building FastJ is a generally simple task. You just need to follow these steps:

- Clone the FastJ Game Library repository.
  ```bash
  git clone https://github.com/lucasstarsz/FastJ-Game-Library.git
  ```
- Build the project.
  ```bash
  ./gradlew build
  ```
  _Having trouble using `gradlew`? Read [this][Terminals Are Different]._

From here, you have successfully built FastJ-Game-Library.


[Windows-Build-Action]: https://github.com/lucasstarsz/FastJ-Game-Library/actions?query=workflow%3ABuild-Windows "Windows Build Status"
[Windows-Build-SVG]: https://github.com/lucasstarsz/FastJ-Game-Library/workflows/Build-Windows/badge.svg

[Ubuntu-Build-Action]: https://github.com/lucasstarsz/FastJ-Game-Library/actions?query=workflow%3ABuild-Ubuntu "Ubuntu Build Status"
[Ubuntu-Build-SVG]: https://github.com/lucasstarsz/FastJ-Game-Library/workflows/Build-Ubuntu/badge.svg

[MacOS-Build-Action]: https://github.com/lucasstarsz/FastJ-Game-Library/actions?query=workflow%3ABuild-MacOS "Mac OS Build Status"
[MacOS-Build-SVG]: https://github.com/lucasstarsz/FastJ-Game-Library/workflows/Build-MacOS/badge.svg

[Maven-Central]: https://maven-badges.herokuapp.com/maven-central/io.github.lucasstarsz.fastj/fastj-library "FastJ on Maven Central"
[Maven-Central-SVG]: https://maven-badges.herokuapp.com/maven-central/io.github.lucasstarsz.fastj/fastj-library/badge.svg

[JavaDoc]: https://javadoc.io/doc/io.github.lucasstarsz.fastj/fastj-library "FastJ Documentation"
[JavaDoc-SVG]: https://javadoc.io/badge2/io.github.lucasstarsz.fastj/fastj-library/javadoc.svg

[fastj-engine link]: https://github.com/lucasstarsz/FastJ-Engine "The original: FastJ Game Engine"

[MIT-License]: LICENSE.txt "MIT Licensing"

[FastJ-Example]: src/example "FastJ Game Library: Example Game"

[Terminals Are Different]: https://gist.github.com/lucasstarsz/9bbc306f8655b916367d557043e498ad "Terminals Access Files Differently"
