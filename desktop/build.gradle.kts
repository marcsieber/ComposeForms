import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
//    kotlin("jvm") version "1.4.30"
    kotlin("multiplatform")
    id("org.jetbrains.compose")
//    id("jacoco")
//    id("org.sonarqube") version "3.1"
}

group = "ch.fhnw"
version = "1.0.0"

kotlin {

    jvm {
        withJava()
    }

    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":common"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KotlinMultiplatformComposeDesktopApplication"
            packageVersion = "1.0.0"
        }
    }
}

//dependencies {
////    implementation(project(":common"))
////    implementation(compose.desktop.currentOs)
//    implementation("org.junit.jupiter:junit-jupiter:5.7.1")
////    implementation("org.jetbrains.kotlin:kotlin-reflect")
//}
//
//tasks {
//    test {
//        useJUnitPlatform()
//    }
//}
//
//tasks.withType<KotlinCompile>() {
//    kotlinOptions.jvmTarget = "11"
//}
//
//compose.desktop {
//    application {
//        mainClass = "MainKt"
//    }
//}
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