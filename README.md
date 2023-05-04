# Java Playground

This repository contains interesting ideas around and/or implementations in the Java language. Each
ideas gets its own Gradle subproject.

## Subprojects

> To build, test, run separate subprojects, you must use the following syntax:

NOTE: Some subprojects may fail their builds by design to show off some interesting part of Java,
so, running base Gradle commands isn't that safe.

```gradle
./gradlew :SUBPROJECT:GRADLE_COMMAND
```

- [duckTyping](./duckTyping/src/main/java/playground/duck/typing/TypeCoercer.java) -> Mimics
  duck-typing in Java with runtime JDK proxies.
