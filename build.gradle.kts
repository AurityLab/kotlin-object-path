group = "com.auritylab"
version = "1.0.0"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.41"
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka") version "0.10.0"
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.10.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.create("sourceJar", Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.create("javadocJar", Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "kotlin-object-path"
            from(components["java"])
            artifact(tasks.getByName("sourceJar"))
            artifact(tasks.getByName("javadocJar"))

            repositories {
                maven {
                    url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                    credentials {
                        username = if (project.hasProperty("ossrhUsername"))
                            project.property("ossrhUsername") as String else null
                        password = if (project.hasProperty("ossrhPassword"))
                            project.property("ossrhPassword") as String else null
                    }
                }
            }

            pom {
                name.set("Kotlin Object Path")
                description.set("Access objects using a simple path")
                url.set("https://github.com/AurityLab/kotlin-object-path")

                organization {
                    name.set("AurityLab UG (haftungsbeschraenkt)")
                    url.set("https://github.com/AurityLab")
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/AurityLab/kotlin-object-path/issues")
                }

                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://github.com/AurityLab/kotlin-object-path/blob/master/LICENSE")
                        distribution.set("repo")
                    }
                }

                scm {
                    url.set("https://github.com/AurityLab/kotlin-object-path")
                    connection.set("scm:git:git://github.com/AurityLab/kotlin-object-path.git")
                    developerConnection.set("scm:git:ssh://git@github.com:AurityLab/kotlin-object-path.git")
                }

                developers {
                    developer {
                        id.set("WipeAir")
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications.getByName("maven"))
}
