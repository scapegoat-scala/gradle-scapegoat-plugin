# Gradle Scapegoat Plugin

[![CircleCI](https://circleci.com/gh/eugene-sy/gradle-scapegoat-plugin.svg?style=shield)](https://circleci.com/gh/eugene-sy/gradle-scapegoat-plugin)

Gradle plugin enables a simple configuration of [Scapegoat](https://github.com/sksamuel/scapegoat) Scala static code analysis tool.
The plugin provides default values for the linter allowing to overwrite them. 

## Configuration

To enable the plugin in the Gradle script:
* add a plugin in the `plugins` section of configuration file;
* add plugin configuration section, if parameters have to be overwritten.

Configuration options are:
* `scapegoatVersion` - version of the Scapegoat Scala compiler plugin, highly suggested adding;
* `scalaVersion` - version of Scala runtime library, highly suggested adding;  
* `dataDir` - Scapegoat reports directory, optional, default value: `${buildDir}/reports/scapegoat`;
* `disabledInspections` - list of disabled inspections, optional, default value: empty list;
* `ignoredFiles` - list of ignored files, optional, default value: empty list;
* `consoleOutput` - a flag enabling console output of the compiler plugin, optional, default value: `true`
* `verbose` - a flag enabling verbose output of the compiler plugin, optional, default value: `true`
* `reports` - list of report types created during compilation, optional, default value: `all`
* `sourcePrefix` - , optional, default value: `src/main/scala`
* `minimalWarnLevel` - minimal level of inspections added to the report, optional, default value: `info`.
* `enable` - enables plugin for build and buildTest actions, default value: `true`
* `testEnable` - enables plugin for buildTest actions, can overwrite `enable` flag, default value: `true`

For the full list of available inspections, check the corresponding [Scapegoat README section](https://github.com/sksamuel/scapegoat#inspections).

For the full list of available compiler options, check [compiler flag section](https://github.com/sksamuel/scapegoat#full-list-of-compiler-flags).

Example configuration:

```groovy
plugins {
  id "com.github.eugenesy.scapegoat" version "0.1.4"
}

scapegoat {
  scapegoatVersion = "1.4.4"
  scalaVersion = "2.12.10"
  dataDir = "${buildDir}/reports/scapegoat"
  disabledInspections = []
  ignoredFiles = []
  consoleOutput = true
  verbose = true
  reports = ["html", "xml"]
  sourcePrefix = "src/main/scala"
  minimalWarnLevel = "info"
  enable = true
  testEnable = true
}
```

```kotlin
plugins {
  id("com.github.eugenesy.scapegoat") version "0.1.4"
}

configure<com.github.eugenesy.scapegoat.ScapegoatExtension>  {
    scapegoatVersion = "1.4.4"
    scalaVersion = "2.12.10"
    dataDir = "${buildDir}/reports/scapegoat"
    disabledInspections = arrayListOf("ArrayEquals", "AvoidToMinusOne")
    ignoredFiles = emptyArray<String>().toList()
    consoleOutput = true
    verbose = true
    reports = arrayListOf<String>("html", "xml")
    sourcePrefix = "src/main/scala"
    minimalWarnLevel = "info"
    enable = true
    testEnable = true
}
```

For more details, check [examples](./example).


## Compatibility

The plugin is dependent on Kotlin runtime, provided by Gradle.
Kotlin API 1.3 is deprecated and is being removed soon.

|Plugin version|Min Gradle Version|Kotlin API|
|:------------:|:----------------:|:--------:|
|0.1.4         |5.3               |1.3       |
|0.2.0         |6.5               |1.4       |

## Changelog

#### 0.1.4

* Fixed a problem with `scalaVersion` and `scapegoatVersion` parameters.
* Added configuration option to enable plugin for sources and tests separately.
* Reports for test tasks are stored in a separate directory.

#### 0.1.3

* Fixed a problem with multiple-value parameters, e.g. `reports`. 
    Now multiple values do not break compilation step because of wrong parameter formatting.
* Improved examples

#### 0.1.0

The initial release of the plugin. 

## License
  
Copyright 2020 Eugene Sypachev

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.