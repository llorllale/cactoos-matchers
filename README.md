[![Managed by Zerocracy](https://www.0crat.com/badge/C63314D6Z.svg)](https://www.0crat.com/p/C63314D6Z)
[![Donate via Zerocracy](https://www.0crat.com/contrib-badge/C63314D6Z.svg)](https://www.0crat.com/contrib/C63314D6Z)

[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/llorllale/cactoos-matchers)](http://www.rultor.com/p/llorllale/cactoos-matchers)

[![Build Status](https://github.com/llorllale/cactoos-matchers/workflows/build/badge.svg)](https://github.com/llorllale/cactoos-matchers/actions)
[![Javadoc](http://www.javadoc.io/badge/org.llorllale/cactoos-matchers.svg)](http://www.javadoc.io/doc/org.llorllale/cactoos-matchers)
[![PDD status](http://www.0pdd.com/svg?name=llorllale/cactoos-matchers)](http://www.0pdd.com/p?name=llorllale/cactoos-matchers)
[![Maven Central](https://img.shields.io/maven-central/v/org.llorllale/cactoos-matchers.svg)](https://maven-badges.herokuapp.com/maven-central/org.llorllale/cactoos-matchers)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/llorllale/cactoos-matchers/blob/master/LICENSE.txt)

[![Test Coverage](https://img.shields.io/codecov/c/github/llorllale/cactoos-matchers.svg)](https://codecov.io/github/llorllale/cactoos-matchers?branch=master)
[![SonarQube](https://img.shields.io/badge/sonar-ok-green.svg)](https://sonarcloud.io/dashboard?id=org.llorllale%3Acactoos-matchers)
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-14-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

## What it is
**cactoos-matchers** is an object-oriented wrapper around hamcrest's matchers.

## Principles
[Design principles](http://www.elegantobjects.org#principles) behind cactoos-matchers.

## How to use
This library depends on [cactoos](https://github.com/yegor256/cactoos) and [hamcrest](https://github.com/hamcrest/JavaHamcrest).
Get the latest version [here](https://github.com/llorllale/cactoos-matchers/releases):

```xml
<dependency>
  <groupId>org.llorllale</groupId>
  <artifactId>cactoos-matchers</artifactId>
  <version>${version}</version>
</dependency>
```

Java version required: 1.8+.

## cactoos-matchers versus Hamcrest + JUnit

cactoos-matchers | Hamcrest (static method) | Hamcrest (object) | JUnit
------ | ------ | ------ | ------
`Assertion` | `MatcherAssert.assertThat` | - | `Assert.assertThat`
`Throws` | - | - | `@expected` + `ExpectedException`
`EndsWith` | `Matchers.endsWith` | `StringEndsWith` | -
`StartsWith` | `Matcers.startsWith` | `StringStartsWith` | -
`TextIs` | `Matchers.is` | `IsEqual` | -
`HasLines` | - | - | -
`MatchesRegex` | - | - | -
`TextHasString` | `Matchers.stringContainsInOrder` | `StringContains` | -
`FuncApplies` | - | - | -
`HasValues` | `Matchers.containsInAnyOrder` | `IsIterableContainingInAnyOrder` | -
`HasValuesMatching` | `Matchers.containsInAnyOrder` | `IsIterableContainingInAnyOrder` | -
`InputHasContent` | - | - | -
`IsTrue` | - | - | `Assert.assertTrue`
`Matches` | - | - | -
`RunsInThreads` | - | - | -
`ScalarHasValue` | - | - | -

## How to use

Use our matchers inside your JUnit [`@Test`](http://junit.sourceforge.net/javadoc/org/junit/Test.html) case. We also provide `Assertion<T>` in which you can compose the behavior under test and the test case's matcher, and attempt to affirm it.

We provide matchers for several different domains:

### Text
Examples:

```java
@Test
public void textHasPrefix() {
  final String prefix = "Application startup";
  new Assertion<>(
    "must have the prefix",
    new TextOf(new File("some.log")),
    new StartsWith(prefix)
  ).affirm();
}  

@Test
public void csvLineHasCorrectFormat() throws Exception {
  final String fields = "^[^|]+|[^|]+|[^|]+$";
  new Assertion<>(
    "must match the expected pattern",
    new FirstOf<>(
      line -> true,
      new Split(
        new TextOf(new File("report.csv")),
        "\n"
      ),
      () -> new TextOf("")
    ).value(),
    new MatchesRegex(fields)
  ).affirm();
}

@Test
public void textIsBlank(){
  new Assertion<>(
    "must be blank",
    new TextOf(
      new File("file.txt")
    ),
    new IsBlank()
  ).affirm();
}
```

### Concurrency
Examples:

*RunsInThreads*
```java
/**
 * Want some assurance that your object is thread-safe?
 */
@Test
public void threadSafety() {
  new Assertion<>(
    "must be able to modify the map concurrently",
    map -> {
      boolean success = true;
      try {
        map.forEach(
          (key, value) -> {
            map.remove(key);
            final Random r = new Random();
            map.put(r.nextInt(), r.nextInt());
          }
        );
      } catch (ConcurrentModificationException ex) {
        success = false;
      }
      return success;
    },
    new RunsInThreads<>(new ConcurrentHashMap<>(), 100)
  ).affirm();
}
```

### Matching errors
Examples:

```java
@Test
public void throwIllegalArgumentExceptionIfLessThan10() throws Exception {
  final Func<Integer, Integer> test = input -> {
    if (input < 10) {
      throw new IllegalArgumentException();
    }
    return input * 10;
  };
  new Assertion<>(
    "must throw illegalargumentexception if input is less than 10",
    () -> test.apply(5),
    new Throws<>(IllegalArgumentException.class)
  ).affirm();
}
```

### Meta-matching: a matcher to test your matchers
Examples:

Use `Matches` to test matchers themselves:

```java
@Test
public void matchExactString() {
  new Assertion<>(
    "must match the exact text",
    new TextIs("abc"),                // matcher being tested
    new Matches<>(new TextOf("abc"))  // reference against which the matcher is tested
  ).affirm();
}
```

## How to contribute?

Just fork the repo and send us a pull request.

Request to add yourself to the list of [contributors](#contributors-) below with
the [`@all-contributors bot`](https://allcontributors.org/docs/en/bot/usage#all-contributors-add).

Make sure your branch builds without any warnings/issues:

```
mvn clean install -Pqulice
```

Note: [Checkstyle](https://en.wikipedia.org/wiki/Checkstyle) is used as a static code analyze tool with
[checks list](http://checkstyle.sourceforge.net/checks.html) in GitHub precommits.


## License (MIT)

Copyright (c) for portions of project cactoos-matchers are held by
Yegor Bugayenko, 2017-2018, as part of project cactoos.
All other copyright for project cactoos-matchers are held by
George Aristy, 2018-2020.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

## Contributors ✨

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://llorllale.github.io/"><img src="https://avatars1.githubusercontent.com/u/2019896?v=4" width="100px;" alt=""/><br /><sub><b>George Aristy</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=llorllale" title="Code">💻</a></td>
    <td align="center"><a href="http://www.yegor256.com"><img src="https://avatars3.githubusercontent.com/u/526301?v=4" width="100px;" alt=""/><br /><sub><b>Yegor Bugayenko</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=yegor256" title="Code">💻</a></td>
    <td align="center"><a href="https://proshin.org"><img src="https://avatars0.githubusercontent.com/u/3031372?v=4" width="100px;" alt=""/><br /><sub><b>Roman Proshin</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=proshin-roman" title="Code">💻</a></td>
    <td align="center"><a href="https://www.linkedin.com/in/lazylead"><img src="https://avatars0.githubusercontent.com/u/1651114?v=4" width="100px;" alt=""/><br /><sub><b>Yurii Dubinka</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=dgroup" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/andreoss"><img src="https://avatars2.githubusercontent.com/u/49783909?v=4" width="100px;" alt=""/><br /><sub><b>andreoss</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=andreoss" title="Code">💻</a></td>
    <td align="center"><a href="http://www.irit.fr/~Victor.Noel/"><img src="https://avatars0.githubusercontent.com/u/160975?v=4" width="100px;" alt=""/><br /><sub><b>Victor Noël</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=victornoel" title="Code">💻</a></td>
  </tr>
  <tr>
    <td align="center"><a href="https://github.com/borysfan"><img src="https://avatars3.githubusercontent.com/u/1413251?v=4" width="100px;" alt=""/><br /><sub><b>Dominik</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=borysfan" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/scristalli"><img src="https://avatars3.githubusercontent.com/u/7326295?v=4" width="100px;" alt=""/><br /><sub><b>Stefano Cristalli</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=scristalli" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/fanifieiev"><img src="https://avatars2.githubusercontent.com/u/3167940?v=4" width="100px;" alt=""/><br /><sub><b>Fevzi Anifieiev</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=fanifieiev" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/paulodamaso"><img src="https://avatars1.githubusercontent.com/u/33915433?v=4" width="100px;" alt=""/><br /><sub><b>Paulo Lobo</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=paulodamaso" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/Vatavuk"><img src="https://avatars2.githubusercontent.com/u/2067023?v=4" width="100px;" alt=""/><br /><sub><b>Vedran Grgo Vatavuk</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=Vatavuk" title="Code">💻</a></td>
    <td align="center"><a href="https://vk.com/sharpler"><img src="https://avatars0.githubusercontent.com/u/2966206?v=4" width="100px;" alt=""/><br /><sub><b>Alexander</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=SharplEr" title="Code">💻</a></td>
  </tr>
  <tr>
    <td align="center"><a href="http://www.vzurauskas.com"><img src="https://avatars0.githubusercontent.com/u/18754033?v=4" width="100px;" alt=""/><br /><sub><b>vzurauskas</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=vzurauskas" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/jsoroka"><img src="https://avatars2.githubusercontent.com/u/527688?v=4" width="100px;" alt=""/><br /><sub><b>jsoroka</b></sub></a><br /><a href="https://github.com/llorllale/cactoos-matchers/commits?author=jsoroka" title="Code">💻</a></td>
  </tr>
</table>

<!-- markdownlint-enable -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->