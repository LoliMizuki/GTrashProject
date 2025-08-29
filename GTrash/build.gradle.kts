import java.io.FileInputStream
import java.util.Properties

//import java.io.FileInputStream
//import java.util.Properties

//apply(plugin: "com.android.library")

plugins {
//    `maven-publish`
//    `java-library`
//    alias(libs.plugins.android.library)
//    id("com.android.library") apply true
    id("maven-publish") // [TODO] test
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.lolimizuki.gtrash"
    compileSdk = 36

    defaultConfig {
        minSdk = 28

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

publishing {

    // test
    val githubProperties = Properties()
    githubProperties.load(FileInputStream(rootProject.file("github.properties")))

    fun aaa(): String {
        return "${rootDir.path}/GTrash/build/outputs/aar/GTrash-release.aar"

//        return rootProject.layout.buildDirectory.get().toString()
    }

    publications {
        create<MavenPublication>("GitHubPackages") {
            run {
                groupId = "com.lolimizuki"
//                artifactId = getArtificatId()
//                version = getVersionName()
                artifactId = "gtrash"
                version = "0.0.1"
//                artifact("$buildDir/outputs/aar/${artifactId}-release.aar")
                artifact(aaa())
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/LoliMizuki/GTrashProject")
            credentials {
                username =
                        githubProperties["gpr.usr"] as String? ?: System.getenv("GPR_USER")
                password =
                        githubProperties["gpr.key"] as String? ?: System.getenv("GPR_API_KEY")
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}