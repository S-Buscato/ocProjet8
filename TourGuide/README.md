# Tour Guide:
_**TourGuide**_ is Spring Boot application that allows its users to see
 any nearby touristic attractions and get discounts on hotel stays as well
 as tickets for different shows.

# Getting Started:

What things you need to install and how will you install them:

- Java 1.8.
- Gradle 4.6 or higher (It is optional since the Gradle wrapper is present in the project).
- Docker (needed only if you want to run the application in a container).
- Your favorite web browser to test the different endpoints
   provided by the application.

# Installing Required Pieces of Software:

A step by step series of examples that tell you how to get a development environment:

1. Install Java:

   https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html


2. Install Gradle (optional):

    https://gradle.org/install/


3. Install Docker:

   https://docs.docker.com/get-docker/

# Running the Application:

##### _You can use one of the following techniques to run the application:_

**1. Run directly from the IDE:**

After downloading and installing all the required pieces of software, you will be ready
 to import the code into your favourite IDE, and run the `Application.java` to start
 the application.

At this stage, the application is ready for use.
  All the endpoints and their documentation can now be accessed via `http://localhost:8080/swagger-ui.html`.

**2. Use the command line:**

The application can be run from the command line(Windows users)/
terminal(Linux and IOS users) by following the steps bellow:

- Open the terminal.
- Browse into the TourGuide folder.
- Run `gradle clean build` or `./gradlew clean build`.
- You can now to browse to the freshly created folder `build` and list its content.
- Navigate to the `libs` directory.
- You can use the `java -jar` followed by the name of the jar file inside the `libs`
  folder.
- The application is now ready and all the endpoints can be accessed via
  `http://localhost:8080/swagger-ui.html`.

**3. Use Docker:**

- Browse to the TourGuide folder.
- Run the `gradle clean build` command.
- Run the `docker build -t tourGuide` command.
- Run the `docker run -p 8080:8080 tourGuide -d` command.
- You can now find the different endpoint by browsing to
  `http://localhost:8080/swagger-ui.html`.

# Testing:

To run the different tests, you can use `gradle test` command or the
 `./gradlew test` command.
 All the Jacoco test reports can be found in the `build/jacocoHtml` folder.