package com.github.eugenesy.scapegoat

import kotlin.test.assertTrue
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class ScapegoatExtensionTest {
    @Test
    fun `extension parameters can be converted to scala compiler arguments`() {
        val ext = ScapegoatExtension()
        val project = ProjectBuilder.builder().build()
        project.repositories.add(project.repositories.mavenCentral())
        project.plugins.apply("com.github.eugenesy.scapegoat")
        project.buildscript.repositories.add(project.repositories.mavenCentral())
        (project as ProjectInternal).evaluate()

        val cfg = project.buildscript.configurations.findByName("scalaScapegoatCompilerPlugin")!!
        val args = ext.buildCompilerArguments(cfg, false)
        assertTrue(args.contains("-Xplugin:${cfg.asPath}"), "Does not contain Scapegoat classpath")
        assertTrue(args.contains("-P:scapegoat:verbose:true"), "Does not contain verbose")
        assertTrue(args.contains("-P:scapegoat:consoleOutput:true"), "Does not contain consoleOutput")
        assertTrue(args.contains("-P:scapegoat:dataDir:"), "Does not contain dataDir")
        assertTrue(args.contains("-P:scapegoat:sourcePrefix:src/main/scala"), "Does not contain sourcePrefix")
        assertTrue(args.contains("-P:scapegoat:minimalWarnLevel:info"), "Does not contain minimalWarnLevel")
    }

    @Test
    fun `disabledInspections are converted into comma-separated-list`() {
        val ext = ScapegoatExtension()
        val inspections = arrayListOf("ArrayEquals", "AvoidToMinusOne")
        ext.disabledInspections = inspections
        val project = ProjectBuilder.builder().build()
        project.repositories.add(project.repositories.mavenCentral())
        project.pluginManager.apply("com.github.eugenesy.scapegoat")
        project.buildscript.repositories.add(project.repositories.mavenCentral())
        (project as ProjectInternal).evaluate()

        val cfg = project.buildscript.configurations.findByName("scalaScapegoatCompilerPlugin")!!
        val args = ext.buildCompilerArguments(cfg, false)
        assertTrue(args.contains("-P:scapegoat:disabledInspections:${inspections.joinToString(separator = ":")}"), "disabledInspections are not converted correctly")
    }

    @Test
    fun `ignoredFiles are converted into comma-separated-list`() {
        val ext = ScapegoatExtension()
        val ignoredFiles = arrayListOf("src/file1", "src/file2")
        ext.ignoredFiles = ignoredFiles
        val project = ProjectBuilder.builder().build()
        project.repositories.add(project.repositories.mavenCentral())
        project.plugins.apply("com.github.eugenesy.scapegoat")
        project.buildscript.repositories.add(project.repositories.mavenCentral())
        (project as ProjectInternal).evaluate()

        val cfg = project.buildscript.configurations.findByName("scalaScapegoatCompilerPlugin")!!
        val args = ext.buildCompilerArguments(cfg, false)
        assertTrue(args.contains("-P:scapegoat:ignoredFiles:${ignoredFiles.joinToString(separator = ":")}"), "ignoredFiles are not converted correctly")
    }

    @Test
    fun `reports are converted into comma-separated-list`() {
        val ext = ScapegoatExtension()
        val reports = arrayListOf("html", "xml")
        ext.reports = reports
        val project = ProjectBuilder.builder().build()
        project.repositories.add(project.repositories.mavenCentral())
        project.plugins.apply("com.github.eugenesy.scapegoat")
        project.buildscript.repositories.add(project.repositories.mavenCentral())
        (project as ProjectInternal).evaluate()

        val cfg = project.buildscript.configurations.findByName("scalaScapegoatCompilerPlugin")!!
        val args = ext.buildCompilerArguments(cfg, false)
        println(args)
        assertTrue(args.contains("-P:scapegoat:reports:${reports.joinToString(separator = ":")}"), "reports are not converted correctly")
    }
}
