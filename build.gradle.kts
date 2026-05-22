plugins {
    alias(libs.plugins.fabric.loom)
}

val mod_version: String by project
val maven_group: String by project
val archives_base_name: String by project

base {
    archivesName = archives_base_name
}
version = mod_version
group = maven_group

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
        rename { "${it}_$archives_base_name" }
    }
}
