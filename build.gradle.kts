plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.shadow)
    alias(libs.plugins.buildconfig)
}

group = "ua.nanit"
version = "1.12.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.slf4j.api)
    implementation(libs.configurate.yaml)

    compileOnly(libs.netty.handler)
    compileOnly(variantOf(libs.netty.transport.native.epoll) { classifier("linux-x86_64") })
    compileOnly(variantOf(libs.netty.transport.native.epoll) { classifier("linux-aarch_64") })
    compileOnly(variantOf(libs.netty.transport.native.io.uring) { classifier("linux-x86_64") })
    compileOnly(variantOf(libs.netty.transport.native.io.uring) { classifier("linux-aarch_64") })
    compileOnly(variantOf(libs.netty.transport.native.kqueue) { classifier("osx-x86_64") })
    compileOnly(variantOf(libs.netty.transport.native.kqueue) { classifier("osx-aarch_64") })

    compileOnly(libs.kyori.adventure.api)
    compileOnly(libs.kyori.adventure.text.serializer.gson)
    compileOnly(libs.kyori.adventure.text.serializer.legacy)
    compileOnly(libs.kyori.adventure.text.serializer.json.legacy.impl)
    compileOnly(libs.kyori.adventure.text.serializer.plain)
    compileOnly(libs.kyori.adventure.text.serializer.minimessage)
    compileOnly(libs.kyori.adventure.nbt)

    compileOnly(libs.gson)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.build {
    dependsOn("shadowJar")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


buildConfig {
    className("BuildConfig")
    packageName("ua.nanit.limbo")
    buildConfigField("LIMBO_VERSION", provider { "${project.version}" })
}

tasks.shadowJar {
    from("LICENSE")

    archiveClassifier.set("")
    archiveVersion.set("")

    minimize()
}

publishing {
    publications {
        create<MavenPublication>("NanoLimbo") {
            artifact(tasks.shadowJar)
        }
    }
}