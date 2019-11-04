plugins {
    id("org.springframework.boot")
    `java-library`
}

dependencies {
    // logger
    api("io.github.microutils:kotlin-logging")
}

val jar: Jar by tasks
jar.enabled = true
