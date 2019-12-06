plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    `java-library`
}

dependencies {
    implementation(project(":lib:app-common"))
    implementation(project(":lib:constant"))
}
