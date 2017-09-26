# wallhaven4j

[![Release](https://jitpack.io/v/com.ivkos/wallhaven4j.svg)](https://jitpack.io/#com.ivkos/wallhaven4j)
[![Build Status](https://travis-ci.org/ivkos/wallhaven4j.svg?branch=master)](https://travis-ci.org/ivkos/wallhaven4j)
[![codecov](https://codecov.io/gh/ivkos/wallhaven4j/branch/master/graph/badge.svg)](https://codecov.io/gh/ivkos/wallhaven4j)

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
    compile 'com.ivkos:wallhaven4j:1.3.0'
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
    <version>1.3.0</version>
</dependency>
```

## Documentation

Javadocs can be found [here](https://jitpack.io/com/ivkos/wallhaven4j/1.3.0/javadoc/).

## Quick Start

**Important note:** This library works by parsing the HTML of Wallhaven. Site design can change unexpectedly and potentially break the library, so until a fix is pushed you are advised to handle `ParseException`s gracefully at any Wallhaven operation. See *Exception Handling* below for more information.

### Creating a Wallhaven session
```java
// anonymous session
Wallhaven wh = new Wallhaven();

// log in with your wallhaven account
Wallhaven wh = new Wallhaven("USERNAME", "PASSWORD");

// log in with account and save session cookies to reuse later
Wallhaven wh = new Wallhaven("USERNAME", "PASSWORD", new File("/path/to/cookies.json"));
```

### Searching
To search for wallpapers you need to first build a `SearchQuery` like so, and then use the `search(...)` methods of the `Wallhaven` object:
```java
SearchQuery query = new SearchQueryBuilder()
            .keywords("cars", "bmw")
            .categories(Category.GENERAL)
            .purity(Purity.SFW)
            .sorting(Sorting.VIEWS)
            .order(Order.DESC)
            .resolutions(new Resolution(1920, 1080), new Resolution(1280, 720))
            .ratios(new Ratio(16, 9))
            .pages(3)
            .build();
    
List<Wallpaper> carWallpapers = wh.search(query);
```

If a filter is omitted, its default value will be used. Default values are defined in `SearchQueryDefaults`.
```java
SearchQuery query = new SearchQueryBuilder()
            .keywords("minimal")
            .categories(Category.GENERAL)
            .ratios(new Ratio(9, 16))
            .pages(3)
            .build();
            
List<Wallpaper> minimalWallpapers = wh.search(query);
```

If your application needs to fetch individual pages on demand, for example when doing lazy loading, you can do this like so:
```java
SearchQuery query = new SearchQueryBuilder()
            .keywords("face")
            .categories(Category.PEOPLE)
            .sorting(Sorting.FAVORITES)
            .build();

// fetch individual pages
List<Wallpaper> page1 = wh.search(query, 1);
List<Wallpaper> page3 = wh.search(query, 3);
```

### Getting Resources by ID
Getting resources by their IDs is easy with the methods of the `Wallhaven` object:

```java
Tag nature = wh.getTag(37);
```

```java
User gandalf = wh.getUser("Gandalf");
```

```java
Wallpaper doggo = wh.getWallpaper(254637);
```

```java
WallpaperCollection wc = wh.getWallpaperCollection("Gandalf", 2);
```

### Exception Handling
**wallhaven4j** parses Wallhaven's HTML. As a consequence, parsing may break if Wallhaven changes its design. It is recommended to be prepared to handle gracefully such cases. wallhaven4j has the following exception hierarchy:

* `WallhavenException`
    - `ConnectionException` - problem with the HTTP connection to Wallhaven
    - `LoginException` - logging in to Wallhaven was unsuccessful due to incorrect credentials
    - `ParseException` - problem parsing Wallhaven's HTML
    - `ResourceNotAccessibleException` - the requested resource cannot be accessed due to privacy or purity restrictions
    - `ResourceNotFoundException` - the requested resource cannot be found
