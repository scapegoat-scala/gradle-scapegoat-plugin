package com.github.eugenesy.scapegoat

import org.gradle.api.Project


object Configuration {
  private const val scalaCompilerConfiguration = "scalaCompilerPlugin"

   fun apply(project: Project) {
    project.buildscript.configurations.create(scalaCompilerConfiguration)
  }
}