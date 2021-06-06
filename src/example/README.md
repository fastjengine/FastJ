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

## Example Programs

### Hello, FastJ!
This program is a remarkably simple introduction to starting work with FastJ. It initializes the engine and runs the engine, which results in an empty window. This also can serve as a project template for working with FastJ.

Command to run: `./gradlew example -PtoRun=hellofastj`

### (Old) Hello, World!
This project is an introduction to working with FastJ, and covers many of the essential topics:
- Engine Initialization/Running
- Window Creation
- Working with a Scene
- Drawing game objects and UI
- Applying Behaviors to Game Objects

Command to run: `./gradlew example -PtoRun=oldhelloworld`

### Bullet Hell
This project takes the concepts we've learned so far and meshes them together into a game!

**WARNING: UNFINISHED PROJECT**

Command to run: `./gradlew example -PtoRun=bullethell`


[Terminals Are Different]: https://gist.github.com/lucasstarsz/9bbc306f8655b916367d557043e498ad "Terminals Access Files Differently"
