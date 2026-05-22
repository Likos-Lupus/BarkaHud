plugins {
    alias(libs.plugins.fabric.loom)
}

val modVersion: String by project
val mavenGroup: String by project
val archivesBaseName: String by project

base {
    archivesName = archivesBaseName
}
version = modVersion
group = mavenGroup

repositories {
    maven {
        url = uri("https://maven.terraformersmc.com/releases/")
    }
    maven {
        url = uri("https://maven.shedaniel.me/")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${libs.versions.minecraft.get()}")

    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)
    implementation(libs.modmenu)

    // to be replace by yacl
    api(libs.cloth.config) {
        exclude(group = "net.fabricmc.fabric-api")
    }
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("*.mod.json") {
        expand("version" to project.version)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release = 25
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_$archivesBaseName" }
    }
}
