import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id(Android.LibraryPluginId)
  kotlin(Kotlin.AndroidPluginId)
  id("common-android-plugin")
  id("maven-publish")
}
group = "com.github.ZuperInc"

android {

  defaultConfig.versionCode = 106
  defaultConfig.versionName = "1.0.6"

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = freeCompilerArgs + "-Xexplicit-api=strict"
    }
  }
  packagingOptions {
    exclude("META-INF/AL2.0")
    exclude("META-INF/LGPL2.1")
  }
}

dependencies {
  implementation(Kotlin.StdLib)
  implementation(Compose.Ui)
  implementation(Compose.UiTooling)
  implementation(Compose.AccompanistPager)
  implementation(Compose.Foundation)
  implementation(Compose.FoundationLayout)
  implementation(Compose.Material)
  implementation(Jakewharton.Threetenabp)
  implementation(Timber.Core)

  testImplementation(Kotest.Assertions)
  testImplementation(Kotest.RunnerJunit5)
  testImplementation(Kotlin.Reflect)

  debugImplementation(ComposeTest.Manifest)
  androidTestImplementation(ComposeTest.Core)
}


afterEvaluate {
  publishing {
    publications {
      create<MavenPublication>("maven") {
        groupId = "com.github.ZuperInc"
        artifactId = "ComposeCalendar"
        version = "1.0.5"
        from(components["release"])
      }
    }
  }
}

//afterEvaluate {
//
//  publishing {
//    publications {
//      create<MavenPublication>("maven") {
//        groupId = "com.github.ZuperInc"
//        artifactId = "ComposeCalendar"
//        version = "1.0.0"
//        artifact(sourcesJar)
//      }
//    }
//  }
//}

//plugins.withId("com.vanniktech.maven.publish") {
//  mavenPublish {
//    sonatypeHost = SonatypeHost.S01
//    releaseSigningEnabled = true
//  }
//}
