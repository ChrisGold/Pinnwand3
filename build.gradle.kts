import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.30"
    application
    `maven-publish`
}

repositories {
    mavenCentral()
    jcenter()
}

val exposedVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    //api group: 'org.jetbrains.kotlin', name: 'kotlin-reflect', version: '1.3.50'
    testImplementation("junit:junit:4.12")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed", "exposed-java-time", exposedVersion)
    implementation("org.xerial:sqlite-jdbc:3.21.0.1")
    implementation("mysql:mysql-connector-java:8.0.21")
    implementation("com.discord4j:discord4j-core:3.1.3")
    implementation("org.slf4j", "slf4j-simple", "2.0.0-alpha1")
    implementation("com.charleskorn.kaml:kaml:0.27.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("Main")
}

val fatJar = task("fatJar", type = Jar::class) {
    this.setProperty("archiveFileName", "Pinnwand-$version.jar")
    manifest {
        attributes["Implementation-Title"] = "Pinnwand"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = "Main"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
            // Include any other artifacts here, like javadocs
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ChrisGold/Pinnwand3")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}