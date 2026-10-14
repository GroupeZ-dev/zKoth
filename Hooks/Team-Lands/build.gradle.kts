dependencies {
    // Latest public Lands 8 API artifact.
    compileOnly("com.incredibleplugins:lands-api:8.0.0")
    testImplementation(project(":API"))
    testImplementation("io.papermc.paper:paper-api:26.2.build.62-beta")
    testImplementation("com.incredibleplugins:lands-api:8.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
    testImplementation("org.mockito:mockito-core:5.18.0")
}

tasks.test {
    useJUnitPlatform()
}
