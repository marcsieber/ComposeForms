
import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version("1.4.32")
    id("org.jetbrains.compose")
    id("jacoco")
    id("org.sonarqube") version "3.1"
}

group = "ch.fhnw"
version = "1.0.0"

sourceSets{
    named("main") {
        dependencies {
            api(compose.desktop.currentOs)
            api("org.jetbrains.kotlin:kotlin-reflect:1.4.32")
            api("org.junit.jupiter:junit-jupiter:5.7.1")
            api("com.hivemq:hivemq-mqtt-client:1.2.1")
            api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

tasks{
    jacocoTestReport {
        reports {
            xml.isEnabled = true
            csv.isEnabled = false
            html.destination = file("${buildDir}/jacocoHtml")
        }
    }
}