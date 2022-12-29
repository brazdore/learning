plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.javatuples:javatuples:1.2")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.google.inject:guice:5.1.0")
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

// Build single JAR file with all dependencies bundled. (OK)
val fatJar = task("fatJar", type = Jar::class) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "org.example.patterns.creational.Prototype"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}

// Copy all dependencies to build directory and use relative path to JARs in classpath. (OK)
tasks.register<Copy>("copyToLibs") {
    from(configurations.runtimeClasspath)
    into("$buildDir/libs")
}

tasks.jar {
    dependsOn("copyToLibs")
    manifest {
        attributes["Main-Class"] = "org.example.patterns.creational.Prototype"
        attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(" ") { it.name }
    }
}

// Add every dependency as absolute path in classpath. (Not very good)
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.example.patterns.creational.Prototype"
        attributes["Class-Path"] =
            configurations.runtimeClasspath.get().joinToString(" ") { "" + "file:///" + it.absolutePath }
    }
}