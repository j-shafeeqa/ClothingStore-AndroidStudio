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
        jcenter()
        //correct format - for forget pw
        maven { url = uri("https://www.jitpack.io" ) }

    }
}

rootProject.name = "Assignment2.4"
include(":app")
 