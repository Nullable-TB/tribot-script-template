import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.tribot.wastedbro.gradle.plugin.TribotPlugin

buildscript {
    repositories {
        maven {
            setUrl("https://gitlab.com/api/v4/projects/22245399/packages/maven")
        }
    }
    dependencies {
        classpath("org.tribot.wastedbro:tribot-gradle-plugin:+")
    }
}

group = "org.tribot.wastedbro"
version = "1.0.0"

plugins {
    java
    kotlin("jvm") version "1.4.10"
    id("org.openjfx.javafxplugin") version "0.0.9"
}
apply<TribotPlugin>()

repositories {
    jcenter()
    // Tribot Central
    maven {
        setUrl("https://gitlab.com/api/v4/projects/20741387/packages/maven")
    }
}

dependencies {
    api("org.tribot:tribot-client:+")
    api("com.google.code.gson:gson:2.8.6")
    api("com.jfoenix:jfoenix:9.0.10")
    api("com.squareup.okhttp3:okhttp:4.6.0")
    api("commons-io:commons-io:2.6")
    api("org.apache.commons:commons-collections4:4.2")
    api("org.apache.commons:commons-lang3:3.8")
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src"))
        }
    }
}

tasks {
    getAt("copyToBin").dependsOn(assemble)
    getAt("repoPackage").dependsOn(assemble)

    classes {
        finalizedBy(getAt("copyToBin"))
        finalizedBy(getAt("repoPackage"))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

configurations.all {
    // Ensures that our dependencies will update timely
    resolutionStrategy.cacheDynamicVersionsFor(5, "minutes")
    resolutionStrategy.cacheChangingModulesFor(5, "minutes")
}