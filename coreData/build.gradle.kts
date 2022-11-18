import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("omr.android.library")
    id("omr.android.hilt")
}

android {
    namespace = "com.lok.dev.coreData"
}

dependencies {

    implementation(libs.bundles.android)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.junit)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(project(":commonUtil"))
    implementation(project(":commonModel"))
    implementation(project(":coreDataBase"))
}