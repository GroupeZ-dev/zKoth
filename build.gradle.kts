plugins {
    `java-library`
    id("com.gradleup.shadow") version "9.0.0-beta11"
}

group = "fr.maxlego08.koth"
version = "1.0.0.0"

extra.set("targetFolder", file("target/"))
extra.set("apiFolder", file("target-api/"))
extra.set("classifier", System.getProperty("archive.classifier"))
extra.set("sha", System.getProperty("github.sha"))

allprojects {

    apply(plugin = "java-library")
    apply(plugin = "com.gradleup.shadow")

    group = "fr.maxlego08.menu"
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
        maven(url = "https://repo.kryptonmc.org/releases")
        maven(url = "https://repo.william278.net/releases")
        maven(url = "https://repo.codemc.org/repository/maven-public")
    }

    tasks.compileJava {
        options.encoding = "UTF-8"
    }

    tasks.javadoc {
        options.encoding = "UTF-8"
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
        compileOnly("me.clip:placeholderapi:2.11.6")
        compileOnly("com.mojang:authlib:3.11.50")
        compileOnly("com.github.booksaw:BetterTeams:4.8.0")
        compileOnly("com.github.MrUniverse44:ScaredClansAPI:0.3")
        compileOnly("me.neznamy:tab-api:4.0.2")
        compileOnly("net.william278:husktowns:3.0.4")
        compileOnly("com.github.angeschossen:LandsAPI:6.44.6")
        compileOnly("com.github.Maxlego08:zSchedulers:1.0.5")
        compileOnly("com.bgsoftware:SuperiorSkyblockAPI:2022.9")
        compileOnly("net.sacredlabyrinth.phaed.simpleclans:SimpleClans:2.15.2")
        compileOnly("com.github.UlrichBR:UClansV7-API:7.13.0-R1")

        compileOnly(files("libs/SternalBoard-2.2.8-all.jar"))
        compileOnly(files("libs/FeatherBoard.jar"))
        compileOnly(files("libs/TitleManager-2.3.6.jar"))
        compileOnly(files("libs/DecentHolograms-2.8.6.jar"))
        compileOnly(files("libs/SaberFactions.jar"))
        compileOnly(files("libs/GangsPlus-2.6.4.jar"))

        implementation("com.github.cryptomorin:XSeries:9.4.0")
        implementation("fr.mrmicky:fastboard:2.1.3")
        implementation("com.github.technicallycoded:FoliaLib:main-SNAPSHOT")
        implementation("com.github.Maxlego08:Sarah:1.17")
    }

}

dependencies {
    api(projects.api)
    // api(projects.hooks)
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
        options.release = 21
    }

    processResources {
        from("resources")
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
}