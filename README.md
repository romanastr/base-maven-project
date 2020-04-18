# Base Project Maven
Speed and consistency of development is essential for a strong software company. 
A base project setup can provide that essential foundation for accelerating development efforts. 
It has
* many common problems already solved,
* many important choices already made,
* many examples of plugins and configurations.

By cloning the base project and customizing it to your needs, you can get running in minutes
and create consistency for months and years ahead.

## Building and running the base project
1. Install Git for your operating system: https://git-scm.com/download/. 
2. Install IntelliJ IDEA for your operating system: https://www.jetbrains.com/idea/download/.
3. Install Docker engine for your operating system and start it: https://docs.docker.com/engine/install/.
4. Start IntelliJ IDEA -> Get from Version Control -> GitHub -> https://github.com/romanastr/base-project-maven.git ->
specify the directory -> Clone.
5. Open Terminal Tool Window in IntelliJ: View -> Tool Windows -> Terminal.
6. Login to docker with `docker login` command (works on any operating system).
7. Switch to your own username on Docker Hub in JIB plugin configuration (see description of the 
plugin below) in [demo/pom.xml](demo/pom.xml) file: 
`<image>index.docker.io/YOUR_USERNAME/YOUR_PROJECT_NAME:YOUR_TAG</image>`.
Specify docker credentials helper for your operating system in parent [pom.xml](pom.xml) as
 `<credHelper>YOUR_HELPER</credHelper>`.  Substitute `YOUR_HELPER` with `desktop` in Windows 10. 
  Check that your chosen helper is available by running `docker-credential-YOUR_HELPER list` 
  command, e.g. `docker-credential-desktop list` on Windows 10. The output should contain a line 
  with your username. 
8. Compile the project from the command line, build the docker image, and push it to your Docker Hub
 by running `mvnw.cmd clean install -P image-build` in the Terminal (works on any operating system). 
The command does not require Maven installed - it downloads Maven from the Internet, 
if it is not found. Alternative, Maven Tool Window in IntelliJ provides very flexible interface for 
working with Maven. 
9. Run the produced docker image on your local machine:
`docker run -p 8080:8080 --rm --name maven YOUR_USERNAME/YOUR_PROJECT_NAME:YOUR_TAG`.
Alternatively, you can run a pre-compiled docker image from the base project:
`docker run -p 8080:8080 --rm --name maven romanastr/base-maven-project:1.0`.
10. Check that the Demo application is live by opening the health check URL in the browser: 
http://localhost:8080/actuator/health - you should see the following JSON response: 
`{"status":"UP"}`.

Now, once you get it working, let us discuss choices and decisions made in the project.
## Maven
Two mainstream dependency management systems for Java applications in 2020 is Maven and Gradle.
Gradle appears more versatile and flexible, thus suitable for large projects. 
Maven has more plugins available and more customization examples online. Maven has a wider 
community support. Thus, Maven is a tool of choice for small to medium size projects.

## Java version
Long-term support (LTS) Java versions are preferred to new Java releases for applications 
in production due to the availability of security and functionality patches.
Java 11 is the latest LTS version with [Java 17 (LTS)](https://en.wikipedia.org/wiki/Java_version_history) 
being the next one. Two major implementations of Java platform is Oracle and OpenJDK community 
(which has a large number of Oracle engineers). Oracle implementations can be used with a monthly
 paid subscription, which allows running commercial software, while OpenJDK builds are free 
for all uses and substantially unrestricted. OpenJDK implementations appear well-supported and 
 frequently updated. Thus, many companies prefer to use free OpenJDK versions of Java, which
we adopt as well. We use the [official OpenJDK images on DockerHub](https://hub.docker.com/_/openjdk) 
to build application docker containers.

## Java frameworks
[Spring Boot 2](https://spring.io/projects/spring-boot) is the most popular Java framework in 2020. 
The set of dependencies tested to work with Spring Boot is defined in `spring-boot-dependencies` 
project. Importing those dependencies from the `pom` dependency instead of making Spring Boot 
a parent provides the most flexibility to adjust the dependencies as well as choose 
a different parent project. The set includes the following test dependencies: JUnit 5 for testing 
and AssertJ for assertions. Lombok annotations reduce boilerplate code, but need to be coupled with 
an IDEA plugin to properly  trace usage  (e.g., Lombok plugin for IntelliJ IDEA). `spring-boot-starter-web` 
dependency provides  embedded  Tomcat to run a web server on, by default on port "8080". 
`spring-boot-starter-actuator`  dependency provides production-ready features for application
monitoring and management including a health check endpoint at "actuator/health" url path.

## Types of tests and test coverage
[The traditional test pyramid](https://medium.com/better-programming/the-test-pyramid-80d77535573) 
heavily relies on the foundations built with the unit tests. They are easy to execute 
and can readily test all special conditions. A smaller set of 
longer running integration tests allows checking functionality of the application 
for a subset of inputs. It is highly beneficial to have 100% code coverage with unit tests.
The difference between 95% code coverage and 100% code coverage is huge - when forced to test
everything, one would naturally simplify the code and eliminate impossible paths, while allowing for
adequate [defensive programming](https://en.wikipedia.org/wiki/Defensive_programming). This readily 
becomes a good habit. The integration tests might not need to have any specific code coverage target, 
but would rather test overall application functionality.

## Maven plugins
The power of Maven in its plugins. The following plugins make the base project work:
* [maven-compiler-plugin](https://maven.apache.org/plugins/maven-compiler-plugin/) facilitates 
compilation with Java compiler. It needs to be explicitly included to specify the Java version;
* [maven-surefire-plugin](http://maven.apache.org/surefire/maven-surefire-plugin/) enables running 
unit tests;
* [maven-failsafe-plugin](http://maven.apache.org/surefire/maven-failsafe-plugin/) enables running 
integration tests.  All test classes with names ending with `IntegrationTest` are considered 
integration tests, while all other test classes are classified as unit tests; 
* [jacoco-maven-plugin](https://tech.asimio.net/2019/04/23/Reporting-Code-Coverage-using-Maven-and-JaCoCo-plugin.html)
enforces code coverage with unit tests. Classes with names ending with `Application` and `Configuration` 
are excluded from code coverage check and from the reports. 100% code line and branch coverage is 
enforced;
* [maven-checkstyle-plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/)
enforces code style. Strict verbose configuration is implemented to
show warnings and fail on them. One of the predefined style definitions, `google_checks.xml` is 
utilized. The same style can be imported to IntelliJ IDEA from [this IntelliJ style file](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml)  
After importing this style, IntelliJ can automatically refactor code in each file to adhere to
the checkstyle. Note that Google style has indentation with 2 spaces instead of a default
IntelliJ indentation with 4 spaces, which takes times to get used to, but it saves space;
* [spring-boot-maven-plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/usage.html) 
facilitates building an executable uber Jar file containing all classes, resources, and dependencies; 
* [maven-jib-plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin) 
enables building Docker images without an explicit Dockerfile. Configuration of a JIB plugin
includes the base docker image with Java. JIB issues a warning, if the base image is defined 
by a name and a tag, since a different image can be later uploaded with the same  `name:tag` combination. 
It is recommended to define the starting image by its hash, which is what we did.
Credentials helper is utilized to integrate with Docker engine and avoid storing passwords in plain text. 
Also, one has to change the DockerHub username in the target image name (see above).
JIB plugin configuration is split between the parent `pom.xml` and the child `pom.xml` to define
the uniform base starting image in the parent, but to define the target image name in the child 
modules individually. Any other Docker registry can be readily used as well.

## Maven profiles
Several profiles are introduced to toggle plugin executions:
* `unit-tests` profile enables executing unit tests and checking test coverage, when active. 
It is active by default / if no profiles are specified;
* `integration-tests` profile enables running integration tests independently of unit tests;
* `image-build` profile enables building Docker image and uploading it to DockerHub registry.

## .gitignore
.gitignore file specifies to Git, which files should be ignored in status checks and never committed
to source control. A sample file we adopted is [crowdsourced in here](https://gist.github.com/dedunumax/54e82214715e35439227).