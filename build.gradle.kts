plugins {
    id("java")
}

group = "com.giwankim"
version = "1.0-SNAPSHOT"

val tomcatVersion = "8.5.99"
val springVersion = "6.1.5"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("javax.servlet:jstl:1.2")
    implementation ("javax.servlet:javax.servlet-api:4.0.1")

    implementation("org.springframework:spring-jdbc:$springVersion")

    runtimeOnly("com.h2database:h2:2.2.224")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("net.ttddyy:datasource-proxy:1.10")


    implementation("ch.qos.logback:logback-classic:1.5.3")

    implementation("org.apache.tomcat.embed:tomcat-embed-core:$tomcatVersion")
    implementation("org.apache.tomcat.embed:tomcat-embed-logging-juli:8.5.2")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:$tomcatVersion")

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.25.3")
}

tasks.test {
    useJUnitPlatform()
}