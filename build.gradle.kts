group = "com.github.eugenesy.scapegoat"

plugins {
    `java-gradle-plugin`
    `maven-publish`

    id("org.jetbrains.kotlin.jvm") version "1.3.41"
}

repositories {
    jcenter()
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
        implementationClass = "com.github.eugenesy.scapegoat.GradleScapegoatPluginPlugin"
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.eugenesy.scapegoat"
            artifactId = "gradle-scapegoat-plugin"
            version = "1.0"

            from(components["java"])
        }
    }
}