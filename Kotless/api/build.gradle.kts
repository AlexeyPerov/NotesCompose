import io.kotless.plugin.gradle.dsl.Webapp.Route53
import io.kotless.plugin.gradle.dsl.kotless

group = rootProject.group
version = rootProject.version

plugins {
    id("io.kotless") version "0.1.7-beta-5" apply true
    kotlin("plugin.serialization") version "1.4.31"
}


dependencies {
    implementation("io.kotless", "kotless-lang", "0.1.7-beta-5")

    implementation("commons-validator", "commons-validator", "1.6")
    implementation("com.amazonaws", "aws-java-sdk-dynamodb", "1.11.650")

    implementation("org.jetbrains.kotlinx", "kotlinx-html-jvm", "0.6.11")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
}

kotless {
    config {
        bucket = ""
        prefix = "logkeeper"

        terraform {
            profile = ""
            region = ""
        }
    }

    webapp {
        route53 = Route53("logkeeper", "kotless.io")
    }

    extensions {
        local {
            useAWSEmulation = false
        }
    }
}

