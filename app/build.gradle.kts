@file:Suppress("DEPRECATION")

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")
}



android {
    namespace = "com.example.guestlogapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.guestlogapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }

    packagingOptions {
        exclude("META-INF/NOTICE.md")
        exclude("META-INF/LICENSE.md")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")

    // Firebase and Google services
    implementation ("com.google.firebase:firebase-database:20.0.5")
    //implementation ("com.google.firebase:firebase-core:21.0.2")
    implementation("com.google.firebase:firebase-auth:21.0.1")
    implementation("com.google.firebase:firebase-auth-ktx:21.0.1")
    implementation("com.google.android.gms:play-services-auth:19.2.0")

    // Email dependencies
    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("com.sun.mail:android-activation:1.6.7")

    // UI and AndroidX dependencies
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.activity:activity-ktx:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation(libs.firebase.database.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.analytics)

    // Test dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
