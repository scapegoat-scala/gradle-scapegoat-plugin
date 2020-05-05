package com.github.eugenesy.scapegoat


class ScapegoatExtension(scapegoatVersion: String,
                         disabledInspections: Array<String>,
                         runAlways: Boolean,
                         ignoredFiles: Array<String>,
                         maxErrors: Int,
                         maxWarnings: Int,
                         maxInfos: Int,
                         consoleOutput: Boolean,
                         outputPath: String,
                         verbose: Boolean,
                         reports: Array<String>,
                         sourcePrefix: String,
                         minimalWarnLevel: String)