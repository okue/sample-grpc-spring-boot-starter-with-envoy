import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.plugins
import groovy.lang.GString
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
    id("java")
    id("com.google.protobuf") version "0.8.10"
    idea
}

group = "com.okue.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    // spring
    implementation("io.github.lognet:grpc-spring-boot-starter:3.4.3")

    // kotlin
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // logger
    implementation("io.github.microutils:kotlin-logging:1.7.6")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.10.0"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.24.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach { task ->
            // https//github.com/google/protobuf-gradle-plugin#generate-descriptor-set-files
            task.generateDescriptorSet = true
            task.descriptorSetOptions.includeSourceInfo = true
            task.descriptorSetOptions.includeImports = true
            task.plugins {
                id("grpc")
            }
        }
    }
}

tasks.register<Copy>("copyGrpcDescriptor") {
    description = "copy grpc description to ../proxy directory"
    group = "proto"
    dependsOn("generateProto")
    from("build/generated/source/proto/main/descriptor_set.desc")
    into("./proxy/")
}

tasks.register<Delete>("cleanUpProto") {
    group = "proto"
    delete(protobuf.protobuf.generatedFilesBaseDir)
}

tasks.register("mySetupProto") {
    group = "proto"
    description = "generate proto files and copy a descriptor set to ./proxy dir"
    dependsOn("cleanUpProto", "generateProto", "copyGrpcDescriptor")
}

idea.module {
    sourceDirs.add(file("${protobuf.protobuf.generatedFilesBaseDir}/main/java"))
    sourceDirs.add(file("${protobuf.protobuf.generatedFilesBaseDir}/main/grpc"))
}

sourceSets {
    main {
        java.srcDirs(
            "build/generated/source/proto/main/java",
            "build/generated/source/proto/main/grpc"
        )
    }
}