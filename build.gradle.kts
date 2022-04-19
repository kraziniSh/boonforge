/*
 Copyright (c) 2022 L9CRO1XX

 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain
 one at https://mozilla.org/MPL/2.0/.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.+"
    kotlin("plugin.serialization") version "1.6.+"
    application
}

group = "com.github.l9cro1xx"
version = "0.0.1"

application {
    mainClass.set("com.github.l9cro1xx.boonforge.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // Clikt: https://github.com/ajalt/clikt/
    implementation("com.github.ajalt.clikt:clikt:3.4.1")
    // kotlinx-serialization: https://github.com/Kotlin/kotlinx.serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    // OkHttp: https://github.com/square/okhttp
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    // yaml-resource-bundle: https://github.com/akkinoc/yaml-resource-bundle
    implementation("dev.akkinoc.util:yaml-resource-bundle:2.3.1")
    // Zip4j: https://github.com/srikanth-lingala/zip4j
    implementation("net.lingala.zip4j:zip4j:2.10.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
