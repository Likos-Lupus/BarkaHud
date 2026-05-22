plugins {
    id("net.fabricmc.fabric-loom") // version declared in settings.gradle.kts
}

val mod_version: String by project
val maven_group: String by project
val archives_base_name: String by project

val depsFabricLoader = property("deps.fabric_loader") as String
val depsFabricApi = property("deps.fabric_api") as String
val depsModmenu = property("deps.modmenu") as String
val depsYacl = property("deps.yacl") as String
val depsJdk = (property("deps.jdk") as String).toInt()

base {
    archivesName = archives_base_name
}
version = "$mod_version+mc${sc.current.version}"
group = maven_group

repositories {
    maven("https://maven.terraformersmc.com/releases/") { name = "TerraformersMC" }
    maven("https://maven.isxander.dev/releases") { name = "Xander Maven" }
}

dependencies {
    minecraft("com.mojang:minecraft:${sc.current.version}")

    implementation("net.fabricmc:fabric-loader:$depsFabricLoader")
    implementation("net.fabricmc.fabric-api:fabric-api:$depsFabricApi")
    implementation("com.terraformersmc:modmenu:$depsModmenu")
    implementation("dev.isxander:yet-another-config-lib:$depsYacl")

    implementation(libs.jspecify)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    useJUnitPlatform()
}

tasks.processResources {
    val deps = mapOf(
        "version" to project.version,
        "minecraft_version" to sc.current.version,
        "minecraft_version_range" to ">=${sc.current.version}",
        "fabric_loader_version" to depsFabricLoader,
        "fabric_api_version" to depsFabricApi,
        "yacl_version" to depsYacl,
        "yacl_version_base" to depsYacl.substringBefore("+")
    )

    inputs.properties(deps)

    filesMatching("*.mod.json") {
        expand(deps)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release = depsJdk
}

java {
    withSourcesJar()
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_$archives_base_name" }
    }
}
