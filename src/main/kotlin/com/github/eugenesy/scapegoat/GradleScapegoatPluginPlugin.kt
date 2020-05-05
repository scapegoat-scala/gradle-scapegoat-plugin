package com.github.eugenesy.scapegoat

import org.gradle.api.Action
import org.gradle.api.Named
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.compile.CompileOptions
import org.gradle.api.tasks.scala.ScalaCompile
import org.gradle.process.CommandLineArgumentProvider
import org.gradle.util.GradleVersion

/**
 * A simple 'hello world' plugin.
 */
class GradleScapegoatPluginPlugin : Plugin<Project> {
  companion object {
    const val PLUGIN_ID = "com.github.eugenesy.scapegoat"
    const val MIN_VERSION = "5.2"
  }


  override fun apply(project: Project) {

    if (GradleVersion.current() < GradleVersion.version(MIN_VERSION)) {
      throw UnsupportedOperationException("$PLUGIN_ID requires at least Gradle $MIN_VERSION")
    }

    val scapegoatConfiguration = ScapegoatConfiguration.apply(project)

    // Register a task
    project.tasks.register("greeting") { task ->
      task.doLast {
        println("Hello from plugin 'com.github.eugenesy.scapegoat'")
      }
    }

    val props = ArrayList<String>()
    props.add("-Xplugin:" + scapegoatConfiguration.asPath)
    props.add("-P:scapegoat:dataDir:" + project.buildDir + "/scapegoat")
    project.tasks.withType(ScalaCompile::class.java).configureEach {
      it.scalaCompileOptions.additionalParameters = props
    }
  }

}
