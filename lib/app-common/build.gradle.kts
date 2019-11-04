plugins {
    id("org.springframework.boot")
    `java-library`
}

dependencies {
    // spring
    api("io.github.lognet:grpc-spring-boot-starter")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // kotlin
    api(kotlin("reflect"))
    api(kotlin("stdlib-jdk8"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")

    // kroto
    api("com.github.marcoferrer.krotoplus:kroto-plus-coroutines")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

val jar: Jar by tasks
jar.enabled = true
