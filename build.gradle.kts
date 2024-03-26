plugins {
    id("java")
}

group = "com.giwankim"
version = "1.0-SNAPSHOT"

val tomcatVersion = "8.5.99"
val springVersion = "5.3.33"
val logbackVersion = "1.5.3"
val junitVersion = "5.10.2"
val assertjVersion = "3.25.3"
val mockitoVersion = "5.11.0"
val h2DatabaseVersion = "2.2.224"

repositories {
    mavenCentral()
}

dependencies {
    implementation("javax.servlet:jstl:1.2")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.springframework:spring-jdbc:$springVersion")
    implementation("org.springframework:spring-test:$springVersion")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("net.ttddyy:datasource-proxy:1.10")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:$tomcatVersion")
    implementation("org.apache.tomcat.embed:tomcat-embed-logging-juli:8.5.2")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:$tomcatVersion")
    runtimeOnly("com.h2database:h2:$h2DatabaseVersion")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation(platform("org.mockito:mockito-bom:$mockitoVersion"))
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}