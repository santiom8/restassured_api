<!-- ABOUT THE PROJECT -->
## Overview

<p>This project is designed to implement and demonstrate API testing using RestAssured with Java. It focuses on creating flexible and maintainable test cases for an API in a development environment. The project leverages a suite of technologies including Java, Maven, RestAssured, POJO, and Cucumber to ensure comprehensive testing coverage, automation, and adherence to coding best practices.</p>

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

- Java JDK 8 or later
- Maven
- An IDE of your choice (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. Clone the repository to your local machine:
   ```bash
   git clone [https://github.com/santiom8/restassured_api.git]
****

2. Navigate to the project directory:
   ```bash
   cd restassured_api
****

3. Install the Maven dependencies:
   ```bash
   mvn clean install
****

<!-- TECHNOLOGIES USED -->
## Technologies Used

<p>The project uses a variety of technologies to ensure high-quality API testing:</p>

- **Java:** The core programming language for writing test cases and utilities.
- **Maven:** Handles project building and dependency management.
- **RestAssured:** Provides a fluent Java DSL for testing HTTP-based REST services.
- **POJO:** Utilizes Plain Old Java Objects for creating entities that represent the structure of request and response data.
- **Cucumber:** Employs Behavior-Driven Development (BDD) principles for writing tests in an understandable format.

<!-- TEST CASES -->
## Test Cases

<p>This project includes four main test cases designed to validate various aspects of the API:</p>

1. **Get the List of Clients:** Tests the retrieval of all clients from the `/api/v1/clients` endpoint.
2. **Get the List of Resources:** Ensures that all resources can be fetched from the `/api/v1/resources` endpoint.
3. **Create a New Client:** Verifies the creation of a new client at the `/api/v1/clients` endpoint.
4. **Update the Last Resource:** Confirms that an existing resource can be updated successfully at the `/api/v1/resources` endpoint.

### API Base URL

<p>The base URL used for the tests is provided by https://63b6dfe11907f863aa04ff81.mockapi.io</p>


<!-- RUNNING THE TESTS -->
## Running the Tests

<p>To execute all the tests, use the following Maven command in your terminal from the root directory of the project:</p>

```bash
mvn test
