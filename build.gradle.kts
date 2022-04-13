import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.+"
    kotlin("plugin.serialization") version "1.6.+"
    application
}

group = "com.github.lacroixx13"
version = "0.0.1"

application {
    mainClass.set("com.github.lacroixx13.boonforge.BoonForgeKt")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.ajalt.clikt:clikt:3.+")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("net.lingala.zip4j:zip4j:2.10.+")
    implementation("dev.akkinoc.util:yaml-resource-bundle:2.+")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
