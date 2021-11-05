group = "com.github.eugenesy.scapegoat"
version = "0.2.0"

plugins {
    `java-gradle-plugin`
    `maven-publish`

    id("org.jetbrains.kotlin.jvm") version "1.4.31"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    id("com.gradle.plugin-publish") version "0.17.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.jetbrains.kotlin:kotlin-test")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

gradlePlugin {
    val scapegoat by plugins.creating {
        id = "com.github.eugenesy.scapegoat"
        implementationClass = "com.github.eugenesy.scapegoat.GradleScapegoatPlugin"
        displayName = "Gradle Scapegoat Plugin"
    }
}

val functionalTestSourceSet = sourceSets.create("functionalTest") {}

gradlePlugin.testSourceSets(functionalTestSourceSet)
configurations.getByName("functionalTestImplementation").extendsFrom(configurations.getByName("testImplementation"))

val functionalTest by tasks.creating(Test::class) {
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
}

val check by tasks.getting(Task::class) {
    dependsOn(functionalTest)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    // This is the version used in Gradle 6.5
    kotlinOptions.apiVersion = "1.4"

    kotlinOptions.allWarningsAsErrors = true
}

ktlint {
    version.set("0.36.0")
    enableExperimentalRules.set(true)
    outputToConsole.set(true)
}

pluginBundle {
    website = "https://github.com/eugene-sy/gradle-scapegoat-plugin"
    vcsUrl = "https://github.com/eugene-sy/gradle-scapegoat-plugin"
    description = "Gradle plugin for Scapegoat Scala compiler static analysis plugin "
    tags = listOf("scala", "scalac", "scapegoat")

    mavenCoordinates {
        groupId = project.group.toString()
        artifactId = project.name
    }
}
