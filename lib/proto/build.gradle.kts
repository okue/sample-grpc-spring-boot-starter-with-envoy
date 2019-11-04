import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("org.springframework.boot")
    id("com.google.protobuf") version "0.8.10"
    idea
    java
    `java-library`
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_12
    targetCompatibility = JavaVersion.VERSION_12
}

dependencies {
    // for protobuf
    implementation("com.github.marcoferrer.krotoplus:kroto-plus-coroutines")
    implementation("com.github.marcoferrer.krotoplus:kroto-plus-message")
    implementation("io.github.lognet:grpc-spring-boot-starter")
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
}

protobuf {
    generatedFilesBaseDir = "$projectDir/generated-java"
    protoc {
        artifact = "com.google.protobuf:protoc:3.10.0"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.24.0"
        }
        id("kroto") {
            artifact = "com.github.marcoferrer.krotoplus:protoc-gen-kroto-plus:0.5.0:jvm8@jar"
        }
    }
    generateProtoTasks {
        val krotoConfig = file("krotoPlusConfig.yml")
        ofSourceSet("main").forEach { task ->
            task.inputs.file(krotoConfig)
            // https//github.com/google/protobuf-gradle-plugin#generate-descriptor-set-files
            task.generateDescriptorSet = true
            task.descriptorSetOptions.includeSourceInfo = true
            task.descriptorSetOptions.includeImports = true
            task.plugins {
                id("grpc")
                id("kroto") {
                    outputSubDir = "java"
                    option("ConfigPath=$krotoConfig")
                }
            }
            task.doFirst {
                delete(protobuf.protobuf.generatedFilesBaseDir)
            }
        }
    }
}

tasks.register<Copy>("copyGrpcDescriptor") {
    group = "proto"
    dependsOn("generateProto")
    from("generated-java/main/descriptor_set.desc")
    into("../../proxy/")
}

tasks.register("mySetupProto") {
    group = "proto"
    description = "generate proto files and copy a descriptor set to ./proxy dir"
    dependsOn("generateProto", "copyGrpcDescriptor")
}

tasks["clean"].doFirst {
    delete(protobuf.protobuf.generatedFilesBaseDir)
}

idea.module {
    sourceDirs.add(file("${protobuf.protobuf.generatedFilesBaseDir}/main/java"))
    sourceDirs.add(file("${protobuf.protobuf.generatedFilesBaseDir}/main/grpc"))
}

sourceSets {
    main {
        java.srcDirs(
            "${protobuf.protobuf.generatedFilesBaseDir}/main/java",
            "${protobuf.protobuf.generatedFilesBaseDir}/main/grpc"
        )
    }
}

val jar: Jar by tasks
jar.enabled = true
