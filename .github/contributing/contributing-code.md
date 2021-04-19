# Contributing Code

I've no clue how I managed to spend 3 years learning software development without using a proper IDE for developing larger projects. From the first days of FastJ I quickly realized I needed a rule set for how my code should be written.

- [IDE](#IDE)
- [Imports](#Imports)
- [General Code Style](#General-Code-Style)
    - [Column Limit](#Column-Limit)
    - [Block Indentation](#Block-Indentation)
    - [Wildcard Imports](#Wildcard-Imports)
    - [Annotations](#Annotations)
    - [Constant Names](#Constant-Names)
    - [Type Variable Names](#Type-Variable-Names)
    - [Camel Case Defined](#Camel-Case-Defined)
- [API Documentation](#API-Documentation)
    - [Be Clear](#Be-Clear)
    - [Be Thoroughly Concise](#Be-Thoroughly-Concise)
- [Website Documentation](#Website-Documentation)


## IDE
I absolutely prefer that you use [IntelliJ IDEA][IntelliJ-Link] while developing FastJ, but [Eclipse][Eclipse-Link] is fairly capable of keeping up as well. VSCode, _I'm personally not a fan of it_ -- it's given me a lot of trouble for Java development in the past, and I don't plan on using it for bigger Java projects any time soon. However, as long as you can get the job done in whatever you're comfortable with you're free to use it.


## Imports
There's a specific order your imports should follow:
```java
import io.github.lucasstarsz.fastj.engine.*;
import io.github.lucasstarsz.fastj.math.*;
import io.github.lucasstarsz.fastj.graphics.*;

import io.github.lucasstarsz.fastj.systems.*;

import java.*;
import javax.*;

import others

import static others

public class ... {
}
```

A bit excessive, but for me (the owner of the repo, hi) it provides the most optimal reading -- not that anyone else reads them.


## General Code Style
For the most part, you can follow along with this codebase using [Google's Java Style Guide][Style-Guide-Link]. It covers most of the bases, with exceptions as follows:

### Column Limit
Other than JUnit test asserts, Markdown, and Git commits -- I don't care about the exact style of these in terms of this, Lines should hard-wrap at the **120-character** limit, and not at 100.

### Block Indentation
Indentation must be **4 spaces**. Indentations for method chaining must be **8 spaces**.
  
### Wildcard Imports
Wildcard imports are only allowed if the file's import count from that specific package is more than 10, with the same situation being applied to static imports more than 7.

### Annotations
For the sake of my own sanity, until further notice I will not allow the exception regarding single parameterless annotations for methods. However:
- Single parameterless annotations for a field are always welcome to be on the same line.
- Otherwise, annotations must always be on a line of its own (one annotation per line).

### Constant Names
All `static final` values should be `PascalCase`, rather than `CONSTANT_CASE`. Of course, this does not change the use of `CONSTANT_CASE` for enum values.
- Furthermore, mutable object constants (e.g. `static final Set<Integer>`) should _also_ use `PascalCase`.

### Type Variable Names
I have no preference on how these are named.

### Camel Case Defined
I will most definitely end up changing this style a lot over time, but do note I prefer `newCustomerID` to `newCustomerId`.


## Documentation
Please see [Contributing Documentation][Contributing-Documentation].

## Unit Tests
Unit Tests are an integral part of the project. No part of the project should be left untested -- I am currently working hard to give this project as much unit testing as possible. 

New code added to the repository should **always** be accompanied by unit testing. There is no exception to this -- PRs without unit tests for new methods added (of course, there are exceptions to this rule) will **not** be merged.


[IntelliJ-Link]: https://www.jetbrains.com/idea/ "IntelliJ IDEA IDE"
[Eclipse-Link]: https://www.eclipse.org/downloads/ "Eclipse IDE"
[Style-Guide-Link]: https://google.github.io/styleguide/javaguide.html "Google Java Style Guide"
[Contributing-Documentation]: contributing-documentation.md "Contributing Documentation"
