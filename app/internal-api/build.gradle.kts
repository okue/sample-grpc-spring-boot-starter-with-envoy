plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":lib:app-common"))
    implementation(project(":lib:constant"))
    implementation(project(":lib:server-interceptor"))
    implementation(project(":lib:logger"))
    implementation(project(":lib:proto"))
}
