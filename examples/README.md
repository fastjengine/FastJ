# Examples

## How to use the examples
To see the examples in action, you'll need to clone the repository.
```bash
git clone https://github.com/fastjengine/FastJ.git
```

Then, run the gradle task `example`. When you do this, you'll need to specify which example you want to run. This can be done by adding `-PtoRun=<example name, without the brackets>`
```bash
./gradlew examples:run -PtoRun=hellofastj
```
_Having trouble using `gradlew`? Read [this][Terminals Are Different]._

_FastJ requires [Java 11][AdoptOpenJDK-Java11-Link] at a minimum._

### I'd like to write my own example
Absolutely do! [Check out how right here](https://github.com/fastjengine/FastJ/blob/main/.github/contributing/contributing-examples.md).

## Informational Examples
_These are examples to help you understand how FastJ works._

### [Hello, FastJ!](http://example.fastj.me/hellofastj/Main.java)
This program is a remarkably simple introduction to starting work with FastJ. It initializes the engine and runs the engine, which results in an empty window. This also can serve as a project template for working with FastJ.

Command to run: `./gradlew examples:run -PtoRun=hellofastj`

### [Logging](http://example.fastj.me/logging/Main.java)
This is designed to show you how logging works in FastJ.

Command to run: `./gradlew examples:run -PtoRun=logging`

### [Engine Configuration](http://example.fastj.me/engineconfig/Main.java)
This program demonstrates the different available configurations for FastJ: Window/Game Resolution, FPS/UPS configuration, and hardware acceleration.

Command to run: `./gradlew examples:run -PtoRun=engineconfig`

### [Keyboard](http://example.fastj.me/keyboard/Main.java)
This program demonstrates how to access keyboard input in FastJ.

Command to run: `./gradlew examples:run -PtoRun=keyboard`

### [Mouse](http://example.fastj.me/mouse/Main.java)
This program demonstrates how to access mouse input in FastJ.

Command to run: `./gradlew examples:run -PtoRun=mouse`

### [Polygons](http://example.fastj.me/polygon2d/Main.java)
This program demonstrates how to create and draw polygons using the `Polygon2D` class.

Command to run: `./gradlew examples:run -PtoRun=polygon2d`

### [Text](http://example.fastj.me/text2d/Main.java)
This program demonstrates how to create and draw text using the `Text2D` class.

Command to run: `./gradlew examples:run -PtoRun=text2d`

### [Models](http://example.fastj.me/model2d/Main.java)
This program demonstrates how to create and draw 2D models using the `Model2D` class.

Command to run: `./gradlew examples:run -PtoRun=model2d`

### [Reading and Writing Models to Files](http://example.fastj.me/modelreadwrite/Main.java)
This program demonstrates how to read and write polygons from and to a file.

Command to run: `./gradlew examples:run -PtoRun=modelreadwrite`

### [Global Rendering Settings](http://example.fastj.me/rendersettings/Main.java)
This program demonstrates how to use the `RenderSettings` class to change the way content gets rendered to the screen in FastJ.

Command to run: `./gradlew examples:run -PtoRun=rendersettings`

### [Audio](http://example.fastj.me/audio/Main.java)
This program demonstrates simple use of FastJ's audio engine, as well as file paths.

Command to run: `./gradlew examples:run -PtoRun=audio`

### [GameObject Behaviors](http://example.fastj.me/behaviors/Main.java)
This program demonstrates how to use the `Behavior` class to control the state of `GameObject`s in FastJ.

Command to run: `./gradlew examples:run -PtoRun=behaviors`

## Games
_Example programs to demonstrate FastJ through games._

### [Bullet Hell](http://example.fastj.me/bullethell)
This project takes the concepts we've learned so far and meshes them together into a game!

**WARNING: UNFINISHED PROJECT**

Command to run: `./gradlew examples:run -PtoRun=bullethell`

### Other Games Made with FastJ
- [Stack Attack](https://github.com/lucasstarsz/stack-attack-fastj) by [lucasstarsz](https://github.com/lucasstarsz)


[Terminals Are Different]: https://gist.github.com/lucasstarsz/9bbc306f8655b916367d557043e498ad "Terminals Access Files Differently"
[AdoptOpenJDK-Java11-Link]: https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot
