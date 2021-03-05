import org.gradle.api.JavaVersion

object Config {
    const val minSdk = 21
    const val versionCode = 1
    const val versionName = "1.0"
    const val applicationId = "com.ahmedvargos.valorantagents"
    const val compileSdk = 30
    const val targetSdk = 30
    val javaVersion = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"
    const val buildTools = "30.0.2"
    const val testInstrumentationRunner = "com.ahmedvargos.base.application.TestAppJUnitRunner"
    const val proguardFiles = "consumer-rules.pro"
}

object Modules {
    const val APP = ":app"
    const val REMOTE = ":core:remote"
    const val LOCAL = ":core:local"
    const val BASE = ":core:base"
    const val NAVIGATOR = ":core:navigator"
    const val HOME = ":features:home"
    const val FAVORITES = ":features:favorites"
    const val AGENTS = ":features:agents_list"
    const val AGENT_DETAILS = ":features:agent_details"
    const val UI_COMPONENTS = ":core:uiComponents"
}

object Versions {
    const val gradle_android = "4.1.1"
    const val kotlin = "1.4.21"
    const val androidx_core = "1.3.2"
    const val multi_dex = "2.0.1"
    const val androidx_lifecycle_ktx = "2.2.0"
    const val palette = "1.0.0"
    const val androidx_fragment_ktx = "1.1.0"
    const val app_compat = "1.2.0"
    const val androidx_recyclerview = "1.0.0"
    const val androidx_constraintLayout = "1.1.3"
    const val material = "1.2.1"
    const val room = "2.2.6"
    const val coroutines_android_version = "1.4.2"

    const val view_pager2 = "1.0.0"
    const val hilt = "2.31.2-alpha"
    const val hilt_viewModel = "1.0.0-alpha02"

    const val retrofit = "2.6.2"
    const val retrofitCoroutines = "0.9.2"
    const val moshi = "1.9.1"
    const val okHttp = "3.12.1"

    const val ktlint = "9.2.1"
    const val ktlint_version = "0.34.2"
    const val glide = "4.8.0"
    const val livedata_testing_lib = "1.1.2"

    const val junit = "4.12"
    const val androidx_ext_junit = "1.1.1"
    const val androidx_testing = "1.2.0"
    const val testing_core = "1.3.0-alpha03"
    const val barista = "3.7.0"
    const val fragments_testing = "1.2.0-rc03"
    const val mockk = "1.10.5"
    const val arch_core_testing = "2.1.0"
    const val coroutines_test = "1.4.2"
}

object Dependencies {
    val kotlin_lib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    val androidx_core = "androidx.core:core-ktx:${Versions.androidx_core}"
    val appcompat = "androidx.appcompat:appcompat:${Versions.app_compat}"
    val androidx_constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraintLayout}"
    val androidx_material = "com.google.android.material:material:${Versions.material}"

    val androidx_lifecycle_ktx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidx_lifecycle_ktx}"

    val androidx_fragment_ktx =
        "androidx.fragment:fragment-ktx:${Versions.androidx_fragment_ktx}"
    val androidx_recyclerview =
        "androidx.recyclerview:recyclerview:${Versions.androidx_recyclerview}"

    val androidx_viewpager2 =
        "androidx.viewpager2:viewpager2:${Versions.view_pager2}"
    val androidx_palette = "androidx.palette:palette:${Versions.palette}"

    val androidx_multidex = "androidx.multidex:multidex:${Versions.multi_dex}"

    val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines_android_version}"

    val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_android_version}"

    //hilt
    val hilt_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt_viewModel}"
    val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    //retrofit
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofit_coroutines_adapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutines}"
    val retrofit_moshi_converter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val retrofit_logger = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"

    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glide_annotations = "com.github.bumptech.glide:compiler:${Versions.glide}"
    val glide_okhttp3 = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"

    //room
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomRunTime = "androidx.room:room-runtime:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    val testlib_junit = "junit:junit:${Versions.junit}"
    val testandroidx_junit = "androidx.test.ext:junit:${Versions.androidx_ext_junit}"
    val testandroidx_runner = "androidx.test:runner:${Versions.androidx_testing}"
    val testandroidx_rules = "androidx.test:rules:${Versions.androidx_testing}"
    val fragments_testing = "androidx.fragment:fragment-testing:${Versions.fragments_testing}"
    val androidx_testing_core = "androidx.test:core:${Versions.testing_core}"
    val mockk = "io.mockk:mockk:${Versions.mockk}"
    val mockk_android = "io.mockk:mockk-android:${Versions.mockk}"
    val livedata_testing = "com.jraska.livedata:testing-ktx:${Versions.livedata_testing_lib}"
    val coroutines_testing =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines_test}"
    val arch_core_testing =
        "androidx.arch.core:core-testing:${Versions.arch_core_testing}"
    val barista =
        "com.schibsted.spain:barista:${Versions.barista}"

    val hilt_ui_testing= "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    val hilt_compiler_testing= "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    val testLibs = arrayOf(
        arch_core_testing,
        coroutines_testing,
        mockk,
        livedata_testing,
        testlib_junit
    )

    val uiTestLibs = arrayOf(
        testandroidx_runner,
        testandroidx_junit,
        testandroidx_rules,
        arch_core_testing,
        coroutines_testing,
        hilt_ui_testing,
        mockk_android,
        livedata_testing,
        roomRunTime
    )
}

object ClassPaths {
    val tools_gradleandroid = "com.android.tools.build:gradle:${Versions.gradle_android}"
    val tools_kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
    val hilt_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

object RemoteServerConfigs {
    const val DEBUG_BASE_URL = "https://valorant-api.com/v1/"
    const val RELEASE_BASE_URL = "https://valorant-api.com/v1/"
}
