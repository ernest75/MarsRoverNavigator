plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.ernestschcneider.marsrovernavigator.data"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        val javaVersion = libs.versions.javaVersion.get()
        sourceCompatibility = JavaVersion.toVersion(javaVersion)
        targetCompatibility = JavaVersion.toVersion(javaVersion)
    }
    kotlinOptions {
        jvmTarget = libs.versions.javaVersion.get()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core:sharedutils"))
    implementation(libs.androidx.ktx)
    implementation(libs.gson)
    implementation(libs.bundles.hilt)

    kapt(libs.hilt.compiler)

    testImplementation(libs.bundles.unit.testing)
    debugImplementation(libs.androidx.ui.tooling)

    testRuntimeOnly(libs.junit.jupiter.engine)

    testImplementation(libs.junit)
    testImplementation(libs.bundles.unit.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
