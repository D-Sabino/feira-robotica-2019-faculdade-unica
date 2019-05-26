plugins {
    kotlin("jvm")
    application
    defaults.`root-module`
    id("com.github.ben-manes.versions") version "0.21.0"
}

dependencies {
    implementation(fileTree("lib"))

    implementation(Deps.unoxCoreJvm)

    implementation(JavaFxDeps.coroutinesJavaFx)
    implementation(JavaFxDeps.tornadoFx)

    TestDeps.core.forEach(::testImplementation)
}

application {
    mainClassName = "br.com.luminaspargere.maze2d.App"
}

tasks.getByName<Jar>("jar") {
    manifest {
        attributes("Main-Class" to "br.com.luminaspargere.maze2d.App")
    }
}
