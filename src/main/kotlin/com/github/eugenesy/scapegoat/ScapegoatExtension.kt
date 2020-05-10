package com.github.eugenesy.scapegoat

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration


open class ScapegoatExtension(var scapegoatVersion: String,
                              var scalaVersion: String,
                              var dataDir: String,
                              var disabledInspections: List<String>,
                              var ignoredFiles: List<String>,
                              var consoleOutput: Boolean,
                              var verbose: Boolean,
                              var reports: List<String>,
                              var sourcePrefix: String,
                              var minimalWarnLevel: String) {
  constructor() : this("1.4.4", "2.12.10", "", ArrayList<String>(), ArrayList<String>(), true, true, arrayListOf("all"), "src/main/scala", "info")

  private fun asCompileArg(name: String, value: String): String = "-P:scapegoat:$name:$value"

  fun toCompilerArguments(configuration: Configuration): List<String> {
    val arguments: MutableList<String> = ArrayList()
    arguments.add("-Xplugin:${configuration.asPath}")
    arguments.add(asCompileArg(VERBOSE, verbose.toString()))
    arguments.add(asCompileArg(CONSOLE_OUTPUT, consoleOutput.toString()))
    arguments.add(asCompileArg(DATA_DIR, dataDir))
    if (disabledInspections.isNotEmpty()) {
      arguments.add(asCompileArg(DISABLED_INSPECTIONS, disabledInspections.joinToString(separator = ",")))
    }
    if (ignoredFiles.isNotEmpty()) {
      arguments.add(asCompileArg(IGNORED_FILES, ignoredFiles.joinToString(separator = ",")))
    }
    if (reports.isNotEmpty()) {
      arguments.add(asCompileArg(REPORTS, reports.joinToString(separator = ",")))
    }
    arguments.add(asCompileArg(SOURCE_PREFIX, sourcePrefix))
    arguments.add(asCompileArg(MINIMAL_WARN_LEVEL, minimalWarnLevel))
    return arguments
  }

  companion object {
    const val EXTENSION_NAME = "scapegoat"
    const val DATA_DIR = "dataDir"
    const val DISABLED_INSPECTIONS = "disabledInspections"
    const val IGNORED_FILES = "ignoredFiles"
    const val VERBOSE = "verbose"
    const val CONSOLE_OUTPUT = "consoleOutput"
    const val REPORTS = "reports"
    const val SOURCE_PREFIX = "sourcePrefix"
    const val MINIMAL_WARN_LEVEL = "minimalWarnLevel"
  }
}

object ScapegoatExtensionHelper {
  private fun <T> resolveValue(project: Project, propertyName: String, defaultValue: T): T {
    if (project.hasProperty(propertyName)) {
      return project.property(propertyName) as T
    }

    return defaultValue
  }

  fun create(project: Project): ScapegoatExtension {
    return project.extensions.create(ScapegoatExtension.EXTENSION_NAME, ScapegoatExtension::class.java)
  }

  fun getExtension(project: Project): ScapegoatExtension {
    return project.extensions.getByType(ScapegoatExtension::class.java)
  }

  fun initialize(project: Project) {
    val extension = getExtension(project)
    extension.scapegoatVersion = resolveValue<String>(project, "scapegoatVersion", "1.4.4")
    extension.scalaVersion = resolveValue<String>(project, "scalaVersion", "2.12.10")
    extension.dataDir = resolveValue<String>(project, "dataDir", "${project.buildDir}/scapegoat")
    extension.disabledInspections = resolveValue<List<String>>(project, "disabledInspections", ArrayList<String>())
    extension.ignoredFiles = resolveValue<List<String>>(project, "ignoredFiles", ArrayList<String>())
    extension.consoleOutput = resolveValue<Boolean>(project, "consoleOutput", true)
    extension.verbose = resolveValue<Boolean>(project, "verbose", true)
    extension.reports = resolveValue<List<String>>(project, "reports", arrayListOf("all"))
    extension.sourcePrefix = resolveValue<String>(project, "sourcePrefix", "src/main/scala")
    extension.minimalWarnLevel = resolveValue<String>(project, "minimalWarnLevel", "info")
  }
}