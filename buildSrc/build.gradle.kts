import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
}
dependencies {
    implementation("com.android.tools.build:gradle:4.2.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.37")
}