plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.tensorflowlitelab12"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.tensorflowlitelab12"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // TensorFlow Lite
    // Using 2.14.0 to avoid LiteRT transition issues while maintaining compatibility
    implementation("org.tensorflow:tensorflow-lite:2.14.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4")
    implementation("org.tensorflow:tensorflow-lite-task-vision:0.4.4")
    implementation("org.tensorflow:tensorflow-lite-task-audio:0.4.4")
    
    // Ensure all TF Lite components use a consistent version to avoid duplicate classes
    constraints {
        implementation("org.tensorflow:tensorflow-lite:2.14.0")
        implementation("org.tensorflow:tensorflow-lite-api:2.14.0")
    }
}