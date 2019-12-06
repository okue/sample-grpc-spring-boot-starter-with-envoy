plugins {
    `java-library`
}

dependencies {
    // spring
    api("io.github.lognet:grpc-spring-boot-starter")

    // kotlin
    api(kotlin("reflect"))
    api(kotlin("stdlib-jdk8"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")

    // kroto
    api("com.github.marcoferrer.krotoplus:kroto-plus-coroutines")
}
