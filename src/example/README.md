# Examples


To see the examples in action, you'll need to clone the repository.
```bash
git clone https://github.com/fastjengine/FastJ.git
```

Then, run the gradle task `example`. When you do this, you'll need to specify which example you want to run. This can be done by adding `-PtoRun=<example name, without the brackets>`
```bash
./gradlew example -PtoRun=helloworld
```
_Having trouble using `gradlew`? Read [this][Terminals Are Different]._


# Example Programs

## Informational
_These are examples to help you understand how FastJ works._

### Hello, FastJ!
This program is a remarkably simple introduction to starting work with FastJ. It initializes the engine and runs the engine, which results in an empty window. This also can serve as a project template for working with FastJ.

Command to run: `./gradlew example -PtoRun=hellofastj`

### Logging
This is designed to show you how logging works in FastJ.

Command to run: `./gradlew example -PtoRun=logging`

### Engine Configuration
This program demonstrates the different available configurations for FastJ: Window/Game Resolution, FPS/UPS configuration, and hardware acceleration.

Command to run: `./gradlew example -PtoRun=engineconfig`

### Keyboard
This program demonstrates how to access keyboard input in FastJ.

Command to run: `./gradlew example -PtoRun=keyboard`

### Polygons
This program demonstrates how to create and draw polygons using the `Polygon2D` class.

Command to run: `./gradlew example -PtoRun=polygon2d`

### Text
This program demonstrates how to create and draw text using the `Text2D` class.

Command to run: `./gradlew example -PtoRun=text2d`

### Models
This program demonstrates how to create and draw 2D models using the `Model2D` class.

Command to run: `./gradlew example -PtoRun=model2d`

### Reading and Writing Models to Files
This program demonstrates how to read and write polygons from and to a file.

Command to run: `./gradlew example -PtoRun=modelreadwrite`

### Global Rendering Settings
This program demonstrates how to use the `RenderSettings` class to change the way content gets rendered to the screen in FastJ.

Command to run: `./gradlew example -PtoRun=rendersettings`

## Games
_Example programs to demonstrate FastJ through games._

### Bullet Hell
This project takes the concepts we've learned so far and meshes them together into a game!

**WARNING: UNFINISHED PROJECT**

Command to run: `./gradlew example -PtoRun=bullethell`


[Terminals Are Different]: https://gist.github.com/lucasstarsz/9bbc306f8655b916367d557043e498ad "Terminals Access Files Differently"
