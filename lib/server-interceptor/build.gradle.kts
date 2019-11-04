plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    `java-library`
}

dependencies {
    implementation(project(":lib:app-common"))
    implementation(project(":lib:constant"))
    implementation(project(":lib:logger"))
}

val jar: Jar by tasks
jar.enabled = true
