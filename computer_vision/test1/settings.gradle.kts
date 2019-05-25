rootProject.name = "test1"

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin."))
                useVersion(Versions.kotlin)
        }
    }
}