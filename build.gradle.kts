import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    // Plugin KSP para generar código en tiempo de compilación ktorfit
    id("com.google.devtools.ksp") version "1.7.20-1.0.8"
    // Serialization
    kotlin("plugin.serialization") version "1.7.20"
    //Dokka Documentación Kotlin
    id("org.jetbrains.dokka") version "1.7.20"
}

group = "resa.mendoza"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")


    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        // Desactivamos el mockito-core para usar mockk
        exclude(module = "mockito-core")
    }
    testImplementation("io.projectreactor:reactor-test")
    // corrutinas-Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    // Mockk
    testImplementation("com.ninja-squad:springmockk:4.0.0")

    // Serializar Json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    // Log
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    // BCrypt
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:1.0.9")

    // KtorFit
    ksp("de.jensklingenberg.ktorfit:ktorfit-ksp:1.0.0-beta16")
    implementation("de.jensklingenberg.ktorfit:ktorfit-lib:1.0.0-beta16")

    // Para serializar en Json con Ktor
    implementation("io.ktor:ktor-client-serialization:2.1.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.1.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.3")

    // Cache
    implementation("io.github.reactivecircus.cache4k:cache4k:0.9.0")

    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.+")

    //Dokka Documentación Kotlin
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.20")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
