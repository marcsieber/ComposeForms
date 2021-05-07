import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") //version "0.3.0"
    id("jacoco")
    id("org.sonarqube") version "3.1"
}

group = "ch.fhnw"
version = "1.0.0"

sourceSets{
    named("main") {
        dependencies {
            api(compose.desktop.currentOs)
            api("org.jetbrains.kotlin:kotlin-reflect")
            api("org.junit.jupiter:junit-jupiter:5.7.1")
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
