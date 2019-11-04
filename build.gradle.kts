import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.1.1"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "9.1.0"
    id("org.springframework.boot") version "2.2.1.RELEASE"
    java
    idea
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
}

allprojects {
    group = "com.okue.midori"
    version = "0.0.1-SNAPSHOT"

    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    ktlint {
        android.set(true)
        verbose.set(true)
        disabledRules.set(setOf("import-ordering"))
        filter {
            exclude { element -> element.file.path.contains("generated-java") }
        }
    }

    detekt {
        reports.xml.enabled = false
        parallel = true
        ignoreFailures = true
    }

    repositories {
        mavenCentral()
    }

    dependencyManagement {
        dependencies {
            dependencySet("com.github.marcoferrer.krotoplus:0.5.0") {
                entry("kroto-plus-coroutines")
                entry("kroto-plus-message")
            }
            dependency("io.github.lognet:grpc-spring-boot-starter:3.4.3")
            dependency("io.github.microutils:kotlin-logging:1.7.6")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.3.2")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "12"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
