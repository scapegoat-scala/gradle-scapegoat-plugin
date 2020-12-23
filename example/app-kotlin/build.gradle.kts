plugins {
    java
    application
    scala
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.12.10")
}

application {
    mainClassName = "com.github.eugenesy.scapegoat.example.app.App"
}

buildscript {
    repositories {
        jcenter()
        mavenLocal()
    }
    dependencies {
        classpath("com.github.eugenesy.scapegoat:gradle-scapegoat-plugin:0.1.4")
    }
}

apply(plugin = "com.github.eugenesy.scapegoat")

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
    testEnable = false
}