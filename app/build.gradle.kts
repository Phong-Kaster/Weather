plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    alias(libs.plugins.baselineprofile)
    id("kotlin-parcelize")
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.weather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weather"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures.buildConfig = true

    flavorDimensions += "type"
    productFlavors {
        create("Development") {
            dimension = "type"
            manifestPlaceholders["accu_weather_key"] = "x5ADcexs7Ge2ge8ZlB8T8JnJtWG4AhSJ"

            buildConfigField("String", "API_URL", "\"https://dataservice.accuweather.com\"")
            buildConfigField("String", "API_KEY", "\"x5ADcexs7Ge2ge8ZlB8T8JnJtWG4AhSJ\"")
            buildConfigField("Boolean", "DEVELOPMENT_ENVIRONMENT", "true")
        }

        create("Production") {
            dimension = "type"
            manifestPlaceholders["accu_weather_key"] = "x5ADcexs7Ge2ge8ZlB8T8JnJtWG4AhSJ"

            buildConfigField("String", "API_URL", "\"https://dataservice.accuweather.com\"")
            buildConfigField("String", "API_KEY", "\"x5ADcexs7Ge2ge8ZlB8T8JnJtWG4AhSJ\"")
            buildConfigField("Boolean", "DEVELOPMENT_ENVIRONMENT", "false")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.profileinstaller)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    "baselineProfile"(project(":baselineprofile"))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /* Retrofit */
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    /* View Model & LiveData for MVVM architecture */
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.livedata)

     /*
     Dependency injection with Hilt - https://developer.android.com/training/dependency-injection/hilt-android#setup
     Hilt Android Processor - https://mvnrepository.com/artifact/com.google.dagger/hilt-android-compiler
     */
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.fragment)

    // Save data in a local database using Room - https://developer.android.com/training/data-storage/room#setup
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    /*Retrofit*/
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.jackson)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.interceptor)


    // Preferences DataStore - https://developer.android.com/topic/libraries/architecture/datastore#preferences-datastore-dependencies
    implementation(libs.androidx.datastore.preferences)


    // Coil - https://coil-kt.github.io/coil/compose/
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)


    // Immutable Collections Library for Kotlin - https://github.com/Kotlin/kotlinx.collections.immutable
    implementation(libs.kotlinx.collections.immutable)


    // Getting started with WorkManager - https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started
    implementation(libs.androidx.work.runtime.ktx)


    // Inject WorkManager with Hilt - https://developer.android.com/training/dependency-injection/hilt-jetpack#workmanager
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
}

// Dependency injection with Hilt - Allow references to generated code
//kapt {
//    correctErrorTypes = true
//}