import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "ch.fhnw"
version = "1.0.0"

sourceSets{
    named("main") {
        dependencies {
            api(compose.desktop.currentOs)
            implementation(project(":common"))
        }
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
