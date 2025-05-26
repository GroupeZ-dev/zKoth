plugins {
    `maven-publish`
}

rootProject.extra.properties["sha"]?.let { sha ->
    version = sha
}

dependencies {
    // compileOnly(files("../libs/zMenu-API-1.1.0.0.jar"))
}

tasks {
    shadowJar {
        relocate("com.tcoded.folialib", "fr.maxlego08.koth.libs.folia")

        destinationDirectory.set(rootProject.extra["apiFolder"] as File)
    }

    build {
        dependsOn(shadowJar)
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${System.getenv("GITHUB_ACTOR")}/zKoth")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        register<MavenPublication>("gpr") {
            // https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#publishing-a-package
            pom {
                groupId = project.group as String?
                name = "${rootProject.name}-${project.name}"
                artifactId = name.get().lowercase()
                version = project.version as String?
            }
            artifact(tasks.shadowJar)
        }
    }
}