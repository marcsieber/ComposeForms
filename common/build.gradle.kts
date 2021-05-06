import org.jetbrains.compose.compose

plugins {
    id("com.android.library")
    kotlin("multiplatform") //version "1.4.30"
    id("org.jetbrains.compose") //version "0.3.0"
//    id("jacoco")
//    id("org.sonarqube") version "3.1"
}


kotlin {
    android()
    jvm("desktop")

    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }
    }
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/AndroidManifest.xml")
//            res.srcDirs("src/main/res")

            dependencies {
                api("androidx.appcompat:appcompat:1.3.0-beta01")
                api("androidx.core:core-ktx:1.3.1")
            }
        }
    }
}

group = "ch.fhnw"
version = "1.0.0"

//dependencies {
//    implementation(compose.desktop.currentOs)
//    implementation("org.junit.jupiter:junit-jupiter:5.7.1")
//    implementation("org.jetbrains.kotlin:kotlin-reflect")
//}

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
//tasks{
//    jacocoTestReport {
//        reports {
//            xml.isEnabled = true
//            csv.isEnabled = false
//            html.destination = file("${buildDir}/jacocoHtml")
//        }
//    }
//}