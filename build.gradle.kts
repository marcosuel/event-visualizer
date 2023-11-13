plugins {
	java
	id("org.springframework.boot") version "2.7.18-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("com.github.davidmc24.gradle.plugin.avro") version "1.3.0"
}

group = "br.com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
	maven { url = uri("https://packages.confluent.io/maven/") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("io.confluent:kafka-avro-serializer:5.3.0")
	implementation("org.apache.avro:avro:1.11.0")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

//tasks.bootBuildImage {
//	builder.set("paketobuildpacks/builder-jammy-base:latest")
//}
