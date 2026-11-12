pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Repositorio oficial de Jitsi con el SDK para Android
        maven { url = uri("https://github.com/jitsi/jitsi-maven-repository/raw/master/releases") }
    }
}

rootProject.name = "LlamadaFamiliar"
include(":app")
