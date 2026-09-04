plugins {
    `java-library`
    id("com.gradleup.shadow") version "9.6.0"
    id("re.alwyn974.groupez.repository") version "1.0.0"
}

group = "fr.maxlego08.koth"
version = "3.2.1"

extra.set("targetFolder", file("target/"))
extra.set("apiFolder", file("target-api/"))
extra.set("classifier", System.getProperty("archive.classifier"))
extra.set("sha", System.getProperty("github.sha"))
val legacyResources = layout.projectDirectory.dir("resources")
val pluginVersion = version.toString()

allprojects {

    apply(plugin = "java-library")
    apply(plugin = "com.gradleup.shadow")
    apply(plugin = "re.alwyn974.groupez.repository")

    group = "fr.maxlego08.koth"
    version = rootProject.version

    repositories {
        mavenLocal()
        mavenCentral()

        maven(url = "https://jitpack.io")
        maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven(url = "https://repo.papermc.io/repository/maven-public/")
        maven(url = "https://oss.sonatype.org/content/groups/public/")
        maven(url = "https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven(url = "https://repo.bg-software.com/repository/api/")
        maven(url = "https://libraries.minecraft.net/")
        maven(url = "https://repo.tcoded.com/releases")
        maven(url = "https://repo.william278.net/releases")
        maven(url = "https://repo.codemc.org/repository/maven-public")
        exclusiveContent {
            forRepository {
                maven("https://dependency.download/releases")
            }

            filter {
                includeGroup("dev.kitteh")
            }
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
        withSourcesJar()
        withJavadocJar()
    }

    tasks.compileJava {
        options.encoding = "UTF-8"
        options.release = 25
        options.compilerArgs.addAll(listOf("-Xlint:deprecation", "-Xlint:removal", "-Xlint:unchecked", "-Werror"))
    }

    tasks.javadoc {
        options.encoding = "UTF-8"
        if (JavaVersion.current().isJava9Compatible)
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:26.2.build.112-stable")

        testImplementation("io.papermc.paper:paper-api:26.2.build.112-stable")
        testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")

        implementation("com.github.cryptomorin:XSeries:13.7.1")
        implementation("fr.mrmicky:fastboard:2.2.0")
        implementation("com.tcoded:FoliaLib:0.5.1")
        implementation("fr.maxlego08.sarah:sarah:1.23")
    }

    tasks.shadowJar {
        archiveBaseName.set("zKoth")
        archiveAppendix.set(if (project.path == ":") "" else project.name)
        archiveClassifier.set("")
    }

    tasks.test {
        useJUnitPlatform()
    }

}

dependencies {
    api(projects.api)

    // Include all hooks dynamically
    file("Hooks").listFiles()?.filter {
        it.isDirectory && !it.name.equals("build")
    }?.forEach { hookDir ->
        implementation(project(":Hooks:${hookDir.name}"))
    }
}

tasks {
    shadowJar {
        relocate("com.tcoded.folialib", "fr.maxlego08.koth.libs.folia")
        relocate("fr.mrmicky.fastboard", "fr.maxlego08.koth.fastboard")

        rootProject.extra.properties["sha"]?.let { sha ->
            archiveClassifier.set("${rootProject.extra.properties["classifier"]}-${sha}")
        } ?: run {
            archiveClassifier.set(rootProject.extra.properties["classifier"] as String?)
        }
        destinationDirectory.set(rootProject.extra["targetFolder"] as File)
    }

    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.release = 25
    }

    processResources {
        from(legacyResources)
        filesMatching("plugin.yml") {
            expand("version" to pluginVersion)
        }
    }
}
