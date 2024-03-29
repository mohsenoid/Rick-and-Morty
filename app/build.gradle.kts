import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.5.31"
    id("org.jetbrains.dokka") version "1.5.30"
    id("androidx.navigation.safeargs.kotlin")

    id("io.gitlab.arturbosch.detekt") version "1.18.1"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"

    jacoco
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.mohsenoid.rickandmorty"

        minSdk = 24
        targetSdk = 31
        multiDexEnabled = true

        versionCode = 10
        versionName = "2.7.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        defaultConfig {
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
        }

        debug {
            isMinifyEnabled = false
            isTestCoverageEnabled = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        getByName("main") {
            java {
                srcDirs("src/main/kotlin")
                srcDirs("${buildDir.absolutePath}/generated/source/kaptKotlin/")
            }
        }
        getByName("test") {
            java {
                srcDirs("src/test/kotlin")
            }
        }
        getByName("androidTest") {
            java {
                srcDirs("src/androidTest/kotlin")
            }
        }
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true

        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        allWarningsAsErrors = true
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }

    lint {
        isIgnoreWarnings = false
        isWarningsAsErrors = true
    }
}

// https://arturbosch.github.io/detekt/#quick-start-with-gradle
detekt {
    allRules = true
    source = files("src/main/kotlin/")
    baseline = file("detekt-baseline.xml")
    buildUponDefaultConfig = true
    reports {
        html { enabled = true }
        xml { enabled = true }
        txt { enabled = false }
    }
}

// https://github.com/JLLeitschuh/ktlint-gradle#configuration
ktlint {
    reporters {
        reporter(ReporterType.HTML)
        reporter(ReporterType.CHECKSTYLE)
    }
}

// https://docs.gradle.org/current/userguide/jacoco_plugin.html
jacoco {
    toolVersion = "0.8.7"
}

dependencies {
    // Android Jetpack
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.multidex:multidex:2.0.1")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Kotlin
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.5.31"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

    implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.5.2"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")

    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.5.30")

    // Test
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.7.2")
    testImplementation("io.mockk:mockk:1.12.1")

    // Koin
    val koinVersion = "3.1.4"
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-android-compat:$koinVersion")
    testImplementation("io.insert-koin:koin-test:$koinVersion")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    val okhttpVersion = "4.9.3"
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")

    // Room
    val roomVersion = "2.3.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // Navigation component
    val navigationComponentVersion = "2.3.5"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationComponentVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationComponentVersion")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navigationComponentVersion")
    androidTestImplementation("androidx.navigation:navigation-testing:$navigationComponentVersion")

    // timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    // chucker
    val chuckerVersion = "3.5.2"
    debugImplementation("com.github.chuckerteam.chucker:library:$chuckerVersion")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:$chuckerVersion")

    // leakcanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")

    // Startup
    implementation("androidx.startup:startup-runtime:1.1.0")
}
