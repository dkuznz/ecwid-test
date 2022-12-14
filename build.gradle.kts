plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    minHeapSize = "512m"
    maxHeapSize = "528m"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
