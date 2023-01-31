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
	application
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
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	// Serializar Json
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

	// Log
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

	// BCrypt
	implementation("com.ToxicBakery.library.bcrypt:bcrypt:1.0.9")

	// KtorFit
	ksp("de.jensklingenberg.ktorfit:ktorfit-ksp:1.0.0-beta16")
	implementation("de.jensklingenberg.ktorfit:ktorfit-lib:1.0.0-beta16")

	// Cache
	implementation("io.github.reactivecircus.cache4k:cache4k:0.9.0")

	//Dokka Documentación Kotlin
	dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.20")
}

application {
	mainClass.set("MongoDbSpringDataReactivoApplicationKt")
}

// https://stackoverflow.com/questions/34855649/invalid-signature-file-digest-for-manifest-main-attributes-exception-while-tryin
tasks {
	val fatJar = register<Jar>("fatJar") {
		dependsOn.addAll(
			listOf(
				"compileJava",
				"compileKotlin",
				"processResources"
			)
		)
		excludes.addAll(
			listOf(
				"META-INF/*.SF",
				"META-INF/*.DSA",
				"META-INF/*.RSA"
			)
		)// We need this for Gradle optimization to work
		duplicatesStrategy = DuplicatesStrategy.EXCLUDE
		manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
		val sourcesMain = sourceSets.main.get()
		val contents = configurations.runtimeClasspath.get()
			.map { if (it.isDirectory) it else zipTree(it) } +
				sourcesMain.output
		from(contents)
	}
	build {
		dependsOn(fatJar) // Trigger fat jar creation during build
	}
}
tasks.withType<Jar> {
	manifest {
		attributes["Main-Class"] = "MainKt"
	}
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
