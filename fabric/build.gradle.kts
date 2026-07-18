plugins {
    alias(libs.plugins.fabric.loom)

    id("java-plugin")
}

project.group = "${rootProject.group}.fabric"
project.version = rootProject.version
project.description = "A nifty mod for Fabric based servers!"

repositories {
    maven("https://maven.fabricmc.net/")
}

dependencies {
    minecraft(libs.minecraft)

    compileOnly(libs.fabric.loader)
    compileOnly(libs.fabric.api)
}