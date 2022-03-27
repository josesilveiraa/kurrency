import org.jetbrains.kotlin.config.KotlinCompilerVersion
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar


plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://repo.codemc.org/repository/maven-public")
}


dependencies {
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:0.37.3")
    implementation("org.jetbrains.exposed:exposed-dao:0.37.3")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.37.3")

    // NBT API
    implementation("de.tr7zw:item-nbt-api:2.9.2")

    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
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
