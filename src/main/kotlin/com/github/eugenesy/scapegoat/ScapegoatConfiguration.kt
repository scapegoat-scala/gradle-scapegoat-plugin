package com.github.eugenesy.scapegoat

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration


object ScapegoatConfiguration {
  private const val SCALA_COMPILER_PLUGIN_CONFIG: String = "scalaScapegoatCompilerPlugin"

  fun apply(project: Project): Configuration {
    val scapegoatConfig = project.buildscript.configurations.create(SCALA_COMPILER_PLUGIN_CONFIG) {
      it.description = "Scapegoat Scala Compiler plugin configuration"
      it.isVisible = true
      it.isCanBeConsumed = false
      it.isCanBeResolved = true
    }

    val scalaVersion = "2.12"
    val scapegoatVersion = "1.3.3"

    val scapegoat = "com.sksamuel.scapegoat:scalac-scapegoat-plugin_${scalaVersion}:${scapegoatVersion}"
    val scapegoatDependency = project.dependencies.create(scapegoat)
    scapegoatConfig.dependencies.add(scapegoatDependency)
    return scapegoatConfig
  }
}