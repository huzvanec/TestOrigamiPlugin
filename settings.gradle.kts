rootProject.name = "TestOrigamiPlugin"
dependencyResolutionManagement{
    repositories {
        maven("https://repo.xenondevs.xyz/releases/")
    }
    
    versionCatalogs {
        create("origamiLibs") {
            from("xyz.xenondevs.origami:origami-catalog:0.1.1")
        }
    }
}

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.xenondevs.xyz/releases/")
    }
}