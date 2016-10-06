# wallhaven4j

[![Release](https://jitpack.io/v/com.ivkos/wallhaven4j.svg)](https://jitpack.io/#com.ivkos/wallhaven4j)
[![Build Status](https://travis-ci.com/ivkos/wallhaven4j.svg?token=4VmYBmCzLTNpvrh5BCJc&branch=master)](https://travis-ci.com/ivkos/wallhaven4j)
[![codecov](https://codecov.io/gh/ivkos/wallhaven4j/branch/master/graph/badge.svg?token=oZ1M8Iuy4G)](https://codecov.io/gh/ivkos/wallhaven4j)

**wallhaven4j** is a Java library that allows you to search for wallpapers on [Wallhaven](https://alpha.wallhaven.cc) and access its resources - wallpapers, collections, tags, and users.

## Requirements
* JRE 7 or higher at runtime
* JDK 8 or higher to compile the library from source

## Installation
### Gradle
**Step 1.** Add the JitPack repository to your root `build.gradle` at the end of repositories:
```
allprojects {
    repositories {
      ...
      maven { url "https://jitpack.io" }
    }
}
```

**Step 2.** Add the dependency:
```
dependencies {
    compile 'com.ivkos:wallhaven4j:1.0.0'
}
```

### Maven
**Step 1.** Add the JitPack repository to your `pom.xml` file:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

**Step 2.** Add the dependency:
```xml
<dependency>
    <groupId>com.ivkos</groupId>
    <artifactId>wallhaven4j</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Quick Start
