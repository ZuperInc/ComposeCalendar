import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id(Android.LibraryPluginId)
  kotlin(Kotlin.AndroidPluginId)
  id("common-android-plugin")
  id("maven-publish")
}

android {

  defaultConfig.versionCode = 106
  defaultConfig.versionName = "1.0.6"

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = freeCompilerArgs + "-Xexplicit-api=strict"
    }
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
}

publishing {
  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/ZuperInc/ComposeCalendar")
      credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }

  publications {
    create<MavenPublication>("maven") {
      groupId = "co.zuper.android"
      artifactId = "compose-calendar"
      version = Versioning.SDK_VERSION
      afterEvaluate {
        from(components["release"])
      }
    }
  }
}
