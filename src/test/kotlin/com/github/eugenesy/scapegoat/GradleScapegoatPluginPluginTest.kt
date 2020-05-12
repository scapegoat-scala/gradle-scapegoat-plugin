package com.github.eugenesy.scapegoat

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import org.gradle.testfixtures.ProjectBuilder

class GradleScapegoatPluginPluginTest {
    @Test
    fun `plugin registers configuration`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.github.eugenesy.scapegoat")

        val cfg = project.buildscript.configurations.findByName("scalaScapegoatCompilerPlugin")
        assertNotNull(cfg, "Configuration is not created")
    }

    @Test
    fun `plugin registers scapegoat dependency`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.github.eugenesy.scapegoat")
        val ext = project.extensions.getByType(ScapegoatExtension::class.java)
        val scapegoat = "com.sksamuel.scapegoat:scalac-scapegoat-plugin_${ext.scalaVersion}:${ext.scapegoatVersion}"
        val scapegoatDependency = project.dependencies.create(scapegoat)
        val cfg = project.buildscript.configurations.findByName("scalaScapegoatCompilerPlugin")

        val contains = cfg?.dependencies?.contains(scapegoatDependency)
        assertNotNull(contains, "Extension does not define dependencies in configuration")
        assertTrue(contains, "Scapegoat dependency is not added")
    }
}
