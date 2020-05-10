package com.github.eugenesy.scapegoat

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.scala.ScalaCompile
import org.gradle.util.GradleVersion

class GradleScapegoatPluginPlugin : Plugin<Project> {
  companion object {
    const val PLUGIN_ID = "com.github.eugenesy.scapegoat"
    const val MIN_VERSION = "5.2"
  }

  override fun apply(project: Project) {

    if (GradleVersion.current() < GradleVersion.version(MIN_VERSION)) {
      throw UnsupportedOperationException("$PLUGIN_ID requires at least Gradle $MIN_VERSION")
    }

    val ext = ScapegoatExtension.apply(project)
    val scapegoatConfiguration = ScapegoatConfiguration.apply(project, ext)

    project.tasks.withType(ScalaCompile::class.java).configureEach {
      it.scalaCompileOptions.additionalParameters = ext.buildCompilerArguments(scapegoatConfiguration)
    }
  }
}
