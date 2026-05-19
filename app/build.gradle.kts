plugins {
    id("com.android.application")
}

// local.properties 에서 Google Maps API 키를 읽어 manifest 에 주입 (CookMap 방식)
val mapsApiKey: String = rootProject.file("local.properties")
    .takeIf { it.exists() }
    ?.readLines()
    ?.firstOrNull { it.trim().startsWith("MAPS_API_KEY=") }
    ?.substringAfter("=")
    ?.trim()
    ?: ""

android {
    namespace = "com.cookandroid.robotinspector"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cookandroid.robotinspector"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("com.google.android.gms:play-services-maps:20.0.0")
}
