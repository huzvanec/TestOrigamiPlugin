plugins {
    kotlin("jvm") version "2.2.0"
    id("com.gradleup.shadow") version "9.0.1"
    id("xyz.jpenilla.run-paper") version "3.0.0-beta.1"
    alias(origamiLibs.plugins.origami)
}

group = "cz.jeme"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.xenondevs.xyz/releases/")
}

origami {
    pluginId = "testorigami"
    paperDevBundle("1.21.8-R0.1-SNAPSHOT")
}

sourceSets.main { java.setSrcDirs(listOf("src/main/kotlin/")) }

dependencies {
    compileOnly(origami.patchedPaperServer())
    compileOnly(origamiLibs.mixin)
    compileOnly(origamiLibs.mixinextras)
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

runPaper {
    disablePluginJarDetection()
}

tasks {
    runServer {
        dependsOn(shadowJar)
        val jarPath = "build/libs/${rootProject.name}-${project.version}.jar"
        pluginJars.from(file(jarPath))
        jvmArgs("-javaagent:../$jarPath", "-Dmixin.debug=true")
        minecraftVersion("1.21.8")
    }

    assemble {
        dependsOn(shadowJar)
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }

    shadowJar {
        archiveClassifier = ""

        addOrigamiLoader()
    }
}