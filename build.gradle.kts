import org.jetbrains.kotlin.config.KotlinCompilerVersion
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar


plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group "com.josesilveiraa.kurrency"
version "1.0-SNAPSHOT"


repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
}


dependencies {
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:0.37.3")
    implementation("org.jetbrains.exposed:exposed-dao:0.37.3")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.37.3")
}

tasks {
    named<ShadowJar>("shadowJar") {
        relocate("co.aikar.commands", "com.josesilveiraa.kurrency.acf")
        relocate("co.aikar.locales", "com.josesilveiraa.kurrency.locales")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
