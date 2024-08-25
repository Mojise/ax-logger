plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.mojise.library.util.log"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

}

afterEvaluate {
    configure<PublishingExtension> {
        publications {
            create<MavenPublication>("debug") {
                from(components["debug"])

                groupId = "com.github.mojise.ax-logger" // 깃허브 이름
                artifactId = "ax-logger" // 공개할 라이브러리의 이름
                version = "0.0.2-beta" // 버전
            }
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.mojise.ax-logger" // 깃허브 이름
                artifactId = "ax-logger" // 공개할 라이브러리의 이름
                version = "0.0.2-beta" // 버전
            }
        }
    }
}