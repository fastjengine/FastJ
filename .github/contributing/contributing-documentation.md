# Documentation

Documentation is incredibly important to any open-source project. Without it, it becomes much harder to actually tell
what's going on without at least using examples. As such, I heavily enforce that it is well-written. (
Obviously, [contributing code][Contributing-Code] and contributing documentation go hand in hand.)

- [API Documentation](#API-Documentation)
    - [Be Clear](#Be-Clear)
    - [Be Thoroughly Concise](#Be-Thoroughly-Concise)
- [Website Documentation](#Website-Documentation)

## API Documentation

API Documentation and source code go hand-in-hand. PRs will **not** go through until accompanying documentation is
satisfactory to me.

### Be Clear

Clarity is an integral part of documentation, without a doubt. Documentation should make it easy for someone with
reading, math, and comprehension skills to understand what a method does, and at a minimum how to use it with a certain
result in mine.

### Be Thoroughly Concise

In the case of API Documentation it makes more sense to be a bit more verbose with describing how certain methods work,
and that's _fine_. If you need to explain the different nuances of why your specific implementation of a rotation method
acts the way it does, go right ahead!

However, it is not acceptable to be _too_ verbose, especially for parts of the code that don't need it. For example, you
don't need a 4-paragraph essay for a getter method (unless that method does some wacky side effects that you just _have_
to explain).

## Website Documentation

I'm not entirely sure how this should be handled just yet -- for now,
please [open an issue using the documentation template][Issues-Link], make your request, and be prepared for a bit of
jank.


[Contributing-Code]: contributing-code.md

[Issues-Link]: https://github.com/lucasstarsz/FastJ/issues/new "Open an issue on FastJ"
