import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.sonarqube") version "3.1"
}

group = "ch.fhnw"
version = "1.0.0"

sourceSets{
    named("main") {
        dependencies {
            api(compose.desktop.currentOs)
            implementation(project(":common"))
            implementation("com.hivemq:hivemq-community-edition-embedded:2021.1")
            implementation("org.jetbrains.compose.material:material-icons-extended-desktop:0.4.0-build182")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev/") }
}
