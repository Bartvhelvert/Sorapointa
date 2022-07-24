@file:Suppress("GradlePackageUpdate")

import com.google.protobuf.gradle.*
import de.fayard.refreshVersions.core.versionFor

plugins {
    `sorapointa-conventions`
    `sorapointa-publish`
    id("com.google.protobuf")
    idea
}

val prop = getRootProjectLocalProps()

ext["no-utils"] = true

dependencies {
    api("com.google.protobuf:protobuf-java:_")
    api("com.google.protobuf:protobuf-kotlin:_")
    api("io.ktor:ktor-utils:_")
}

protobuf {
    generatedFilesBaseDir = "$projectDir/src/generated/"

    generateProtoTasks {
        ofSourceSet("main").forEach { task ->
            task.builtins {
                id("kotlin") {}
            }
            if (prop["proto.fullCompile"] == "true") {
                task.doFirst {
                    delete(task.outputs)
                }
            }
        }
    }

    protoc {
        artifact = "com.google.protobuf:protoc:${versionFor("version.com.google.protobuf..protoc")}"
    }
}

sourceSets {
    main {
        proto {
            setSrcDirs(setOf("src/proto"))
        }
        java {
            srcDir("src/generated")
        }
    }
}

idea {
    module {
        sourceDirs.plus(file("src/proto"))
    }
}

tasks.withType<Javadoc> {
    exclude("**/*OuterClass*")
}
