plugins {
    id("omr.android.application")
    id("omr.android.hilt")
    id("org.jetbrains.kotlin.android")
}

android {
    defaultConfig {
        applicationId = "com.lok.dev.omrchecker"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.bundles.android)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.junit)

    implementation(libs.hilt.android)
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.4.+")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    kapt(libs.hilt.compiler)

    implementation(project(":coreData"))
    implementation(project(":coreDataBase"))
    implementation(project(":commonBase"))
    implementation(project(":commonModel"))
    implementation(project(":commonUtil"))
}