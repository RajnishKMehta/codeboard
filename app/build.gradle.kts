plugins {
    id("com.android.application")
}

android {
    namespace = "com.gazlaws.codeboard"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.gazlaws.codeboard"
        minSdk = 23
        targetSdk = 37
        versionCode = 23
        versionName = "6.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyStore = file(System.getenv("KEYSTORE_FILE") ?: "/tmp/release.keystore")
            keyStorePassword = System.getenv("KEYSTORE_PASSWORD") ?: "codeboard123"
            keyAlias = System.getenv("KEY_ALIAS") ?: "release-key"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "codeboard123"
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix =
              System.getenv("GITHUB_RUN_NUMBER")
                ?.let { "-debug-$it" }
                ?: "-debug"

        }
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.preference:preference:1.2.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.github.AppIntro:AppIntro:6.3.1")
    implementation("com.github.evilbunny2008:android-material-color-picker-dialog:1.3.7")

    testImplementation("junit:junit:4.13.2")
    implementation("androidx.annotation:annotation:1.10.0")
    androidTestImplementation("androidx.annotation:annotation:1.10.0")
    androidTestImplementation("androidx.test:core:1.7.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
}
