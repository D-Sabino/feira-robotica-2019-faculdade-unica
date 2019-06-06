import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    defaults.`root-module`
    id("com.github.ben-manes.versions") version "0.21.0"
}

dependencies {
    implementation(fileTree("lib"))

    implementation(Deps.koinCore)
    implementation(Deps.okHttp)
    implementation(Deps.okHttpLoggingInterceptor)
    implementation(Deps.retrofit)
    implementation(Deps.unoxCoreJvm)

    implementation(JavaFxDeps.coroutinesJavaFx)
    implementation(JavaFxDeps.ikonliJavaFx)
    implementation(JavaFxDeps.ikonliMaterialIcons)
    implementation(JavaFxDeps.ikonliMaterialDesign)
    implementation(JavaFxDeps.jFoenix)
    implementation(JavaFxDeps.tornadoFx)

    TestDeps.core.forEach(::testImplementation)
}

application {
    mainClassName = "br.com.luminaspargere.mazerunner.App"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.getByName<Jar>("jar") {
    manifest {
        attributes("Main-Class" to "br.com.luminaspargere.mazerunner.App")
    }
}
