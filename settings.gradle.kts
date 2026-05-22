pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
        gradlePluginPortal()
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.4"
    id("net.fabricmc.fabric-loom") version "1.15.+" apply false
    id("net.fabricmc.fabric-loom-remap") version "1.15.+" apply false
}

stonecutter {
    create(rootProject) {
        versions("26.1").buildscript("build-26.gradle.kts")
        versions("1.21.11", "1.21.1").buildscript("build-1_21.gradle.kts")
        vcsVersion = "26.1"
    }
}
