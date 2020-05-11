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
  constructor() : this(DEFAULT_SCAPEGOAT_VERSION, DEFAULT_SCALA_VERSION, "", ArrayList<String>(), ArrayList<String>(), true, true, arrayListOf(DEFAULT_REPORTS), DEFAULT_SOURCE_PREFIX, DEFAULT_MIN_WARN_LEVEL)

  private fun asCompileArg(name: String, value: String): String = "-P:scapegoat:$name:$value"

  private fun asCompileArg(name: String, value: List<String>): String = "-P:scapegoat:$name:${value.joinToString(separator = ",")}"

  fun buildCompilerArguments(configuration: Configuration): List<String> {
    val arguments: MutableList<String> = ArrayList()
    arguments.add("-Xplugin:${configuration.asPath}")
    arguments.add(asCompileArg(VERBOSE, verbose.toString()))
    arguments.add(asCompileArg(CONSOLE_OUTPUT, consoleOutput.toString()))
    arguments.add(asCompileArg(DATA_DIR, dataDir))
    if (disabledInspections.isNotEmpty()) {
      arguments.add(asCompileArg(DISABLED_INSPECTIONS, disabledInspections))
    }
    if (ignoredFiles.isNotEmpty()) {
      arguments.add(asCompileArg(IGNORED_FILES, ignoredFiles))
    }
    if (reports.isNotEmpty()) {
      arguments.add(asCompileArg(REPORTS, reports))
    }
    arguments.add(asCompileArg(SOURCE_PREFIX, sourcePrefix))
    arguments.add(asCompileArg(MINIMAL_WARN_LEVEL, minimalWarnLevel))
    return arguments
  }

  companion object {
    private const val EXTENSION_NAME = "scapegoat"
    const val SCAPEGOAT_VERSION = "scapegoatVersion"
    const val SCALA_VERSION = "scalaVersion"
    const val DATA_DIR = "dataDir"
    const val DISABLED_INSPECTIONS = "disabledInspections"
    const val IGNORED_FILES = "ignoredFiles"
    const val VERBOSE = "verbose"
    const val CONSOLE_OUTPUT = "consoleOutput"
    const val REPORTS = "reports"
    const val SOURCE_PREFIX = "sourcePrefix"
    const val MINIMAL_WARN_LEVEL = "minimalWarnLevel"

    const val DEFAULT_SCAPEGOAT_VERSION = "1.4.4"
    const val DEFAULT_SCALA_VERSION = "2.12.10"
    const val DEFAULT_REPORTS = "all"
    const val DEFAULT_SOURCE_PREFIX = "src/main/scala"
    const val DEFAULT_REPORTS_PATH = "reports/scapegoat"
    const val DEFAULT_MIN_WARN_LEVEL = "info"

    private fun <T> resolveValue(project: Project, propertyName: String, defaultValue: T): T {
      if (project.hasProperty(propertyName)) {
        return project.property(propertyName) as T
      }

      return defaultValue
    }

    private fun create(project: Project): ScapegoatExtension {
      return project.extensions.create(EXTENSION_NAME, ScapegoatExtension::class.java)
    }

    fun getExtension(project: Project): ScapegoatExtension {
      return project.extensions.getByType(ScapegoatExtension::class.java)
    }

    private fun initialize(project: Project) {
      val extension = getExtension(project)
      extension.scapegoatVersion = resolveValue<String>(project, SCAPEGOAT_VERSION, DEFAULT_SCAPEGOAT_VERSION)
      extension.scalaVersion = resolveValue<String>(project, SCALA_VERSION, DEFAULT_SCALA_VERSION)
      extension.dataDir = resolveValue<String>(project, DATA_DIR, "${project.buildDir}/${DEFAULT_REPORTS_PATH}")
      extension.disabledInspections = resolveValue<List<String>>(project, DISABLED_INSPECTIONS, ArrayList<String>())
      extension.ignoredFiles = resolveValue<List<String>>(project, IGNORED_FILES, ArrayList<String>())
      extension.consoleOutput = resolveValue<Boolean>(project, CONSOLE_OUTPUT, true)
      extension.verbose = resolveValue<Boolean>(project, VERBOSE, true)
      extension.reports = resolveValue<List<String>>(project, REPORTS, arrayListOf(DEFAULT_REPORTS))
      extension.sourcePrefix = resolveValue<String>(project, SOURCE_PREFIX, DEFAULT_SOURCE_PREFIX)
      extension.minimalWarnLevel = resolveValue<String>(project, MINIMAL_WARN_LEVEL, DEFAULT_MIN_WARN_LEVEL)
    }

    fun apply(project: Project): ScapegoatExtension {
      val ext = create(project)
      initialize(project)
      return ext
    }
  }
}
