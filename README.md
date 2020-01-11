# Kotlin Object Path
![GitHub Actions](https://github.com/AurityLab/kotlin-object-path/workflows/Gradle/badge.svg)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
![Maven Central](https://img.shields.io/maven-central/v/com.auritylab/kotlin-object-path?label=codegen)

A library for [Kotlin](https://kotlinlang.org/) which lets you access properties and lists using a simple path (e.g "users.0.name").

Supports simple reflective access and is compatible with parsed JSON values from Jackson/Gson.

Inspired by [object-path](https://github.com/mariocasciaro/object-path).

## Install
#### Gradle
```kotlin
dependencies {
    implementation("com.auritylab:kotlin-object-path:1.0.0")
}
```
#### Maven
```xml
<dependency>
    <groupId>com.auritylab</groupId>
    <artifactId>kotlin-object-path</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage
```kotlin
// Simple container which holds three values.
data class Container(val a: Any, val b: Any, val c: Any)

// Create an instance with some simple values.
val value = Container("0a", "0b", Container("0c1a", listOf("0c1b0", "0c1b1", "0c1b2"), mapOf(Pair("aa", "0c1c0"), Pair("bb", "0c1c2"))))

// Get a value:
KObjectPath(value).path("c.a").get() // returns "0c1a"
KObjectPath(value).path("c.b.0").get() // returns "0c1b0"
KObjectPath(value).path("c.c.aa").get() // returns "0c1c0"

// Set a value (Currently only supported on mutable Maps and Lists):
KObjectPath(value).path("c.b.0").set("new value")
KObjectPath(value).path("c.c.aa").set("new value")
```
