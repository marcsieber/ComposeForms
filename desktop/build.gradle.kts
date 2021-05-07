import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
//    kotlin("jvm") //version "1.4.30"
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("jacoco")
    id("org.sonarqube") version "3.1"
}

group = "ch.fhnw"
version = "1.0.0"

kotlin {
    jvm {}
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":common"))
//                implementation("org.junit.jupiter:junit-jupiter:5.7.1")
            }
        }
    }
//    sourceSets.main {
//        kotlin.srcDirs("src/jvmMain/kotlin")
//        dependencies {
//            implementation(compose.desktop.currentOs)
//            implementation(project(":common"))
//        }
//    }
}

compose.desktop {
    application {
        mainClass = "ch.fhnw.forms.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ch.fhnw.forms.desktop"
            packageVersion = "1.0.0"
        }
    }
}

//dependencies {
//    implementation(project(":common"))
//    implementation(compose.desktop.currentOs)
//    implementation("org.junit.jupiter:junit-jupiter:5.7.1")
//    implementation("org.jetbrains.kotlin:kotlin-reflect")
//}


tasks.withType<Test> {
    useJUnitPlatform()
}


//tasks {
//    test {
//        useJUnitPlatform()
//    }
//}
//

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

//
//
//tasks{
//    jacocoTestReport {
//        reports {
//            xml.isEnabled = true
//            csv.isEnabled = false
//            html.destination = file("${buildDir}/jacocoHtml")
//        }
//    }
//}