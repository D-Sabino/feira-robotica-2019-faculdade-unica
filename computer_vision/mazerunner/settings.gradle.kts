rootProject.name = "mazerunner"

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin."))
                useVersion(Versions.kotlin)
        }
    }
}