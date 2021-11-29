plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")

}

android {
    compileSdkVersion(31)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId =  "com.attendance.student"
        minSdkVersion(23)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.4.0")

    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-runtime-ktx:2.3.5")
    implementation("androidx.fragment:fragment-ktx:1.3.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
    implementation("com.google.dagger:hilt-android:2.37")
    kapt("com.google.dagger:hilt-compiler:2.37")

    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")

    implementation("com.budiyev.android:code-scanner:2.1.0")

    implementation("ch.acra:acra-mail:5.8.4")
    implementation("ch.acra:acra-dialog:5.8.4")

    implementation("com.google.code.gson:gson:2.8.6")

    implementation("com.google.firebase:firebase-database:20.0.0")

    implementation("com.github.prabhat1707:EasyWayLocation:2.2")


    implementation("androidx.room:room-ktx:2.4.0-beta02")
    kapt("androidx.room:room-compiler:2.4.0-beta02")

}