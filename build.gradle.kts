/*
 Copyright (c) 2022 L9CRO1XX

 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can
 obtain one at https://mozilla.org/MPL/2.0/.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.serialization") version "1.6.21"
    application
}

group = "com.github.l9cro1xx"
version = "0.1.0"

application {
    mainClass.set("com.github.l9cro1xx.boonforge.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation(kotlin("test"))
    // Clikt: https://github.com/ajalt/clikt/
    implementation("com.github.ajalt.clikt:clikt:3.4.2")
    // kotlinx-coroutines: https://github.com/Kotlin/kotlinx.coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    // kotlinx-serialization: https://github.com/Kotlin/kotlinx.serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    // OkHttp: https://github.com/square/okhttp
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    // Zip4j: https://github.com/srikanth-lingala/zip4j
    implementation("net.lingala.zip4j:zip4j:2.10.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
