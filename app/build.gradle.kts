plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.familia.llamada"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.familia.llamada"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.core:core-ktx:1.13.1")

    // SDK oficial de Jitsi Meet. La documentación oficial usa "+" (última versión).
    // Cuando la app funcione bien, conviene fijar la versión exacta que se descargó
    // (aparece en el panel de dependencias de Android Studio) para que no cambie sola.
    implementation("org.jitsi.react:jitsi-meet-sdk:+") { isTransitive = true }
}
