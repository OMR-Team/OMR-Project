import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("omr.android.library")
    id("omr.android.hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.lok.dev.coreDataBase"
}

dependencies {

    implementation(libs.bundles.android)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.junit)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.coroutine)
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)
    implementation(libs.gson)

    implementation(project(":commonUtil"))
    implementation(project(":commonModel"))
}