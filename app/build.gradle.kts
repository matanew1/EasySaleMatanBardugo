plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.easysalematanbardugo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.easysalematanbardugo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    //added
    implementation(libs.retrofit)

    implementation(libs.converter.gson)

    implementation(libs.room.runtime)

    annotationProcessor(libs.room.compiler)

    implementation(libs.recyclerview)

    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    implementation(libs.lottie)



    // base
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(kotlin("script-runtime"))

}