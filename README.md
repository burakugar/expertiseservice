
# Expertise Service

This is a Java-based service for managing expertise sessions and responses. It's part of the larger Tiktak application.

### Prerequisites

- Java 17 or higher
- Maven
- Spring Boot v 3.2.1 

### Installing

1. Clone the repository:
```sh
git clone https://github.com/burakugar/expertiseservice.git
```

2. Navigate into the project directory:
```sh
cd expertiseservice
```

3. Use Maven to build the project:
```sh
./mvnw clean install
```

4. Run the application:
```sh
./mvnw spring-boot:run
```

The service will start and listen on localhost:8080.

### Swagger

Access the Swagger UI at: http://localhost:8080/swagger-ui/index.html

### H2 DATABASE UI

Access the H2 console at: http://localhost:8080/h2-console/

## Components of the Project

### DTOs

- `ExpertiseResponseDTO`: This is the data transfer object for expertise responses. It contains a list of `AnswerDTO` objects. Each `AnswerDTO` has a `questionId`, `answer`, and `photoUrl`.

- `QuestionDTO`: This is the data transfer object for questions. It contains `questionId`, `content`, and `photoUrl`.

- `CarDTO`: This is the data transfer object for cars. It contains `carId`.

- `ErrorDto`: This is the data transfer object for errors. It contains `status`, `message`, and `details`.

### Entities

- `Car`: This is the entity for cars. It has an `id`.

- `Question`: This is the entity for questions. It has an `id`, `content`, `photoUrl`, and a reference to the `ExpertiseSession` it belongs to.

- `ExpertiseSession`: This is the entity for expertise sessions. It has an `id`, `status`, `car`, and a list of `Question` entities.

### Validations

- `ExpertiseResponseDTO`: The list of `AnswerDTO` objects must not be empty.

- `AnswerDTO`: The `questionId` must not be null. The `answer` must not be blank and its size must not exceed 4 characters. The `photoUrl` must be a valid URL.

- `QuestionDTO`: The `questionId` and `content` must not be null. The `photoUrl` must be a valid URL and not blank.

- `CarDTO`: The `carId` must not be null.

### Exceptions

- `MethodArgumentNotValidException`: This exception is thrown when validation on an argument annotated with `@Valid` fails.

- `ConstraintViolationException`: This exception is thrown when a constraint violation occurs.

- `CarNotFoundException`: This exception is thrown when a car is not found.

- `ExpertiseSessionNotFoundException`: This exception is thrown when an expertise session is not found.

- `QuestionNotFoundException`: This exception is thrown when a question is not found.

- `InvalidExpertiseResponseException`: This exception is thrown when an expertise response is invalid.

### Controllers

- `ExpertiseController`: This controller handles requests related to expertise sessions and responses. It has two methods: `getQuestions` and `saveExpertiseResponse`.

### Services

- `ExpertiseServiceImpl`: This service handles the business logic for expertise sessions and responses. It has two methods: `getQuestionsForCar` and `saveExpertiseResponse`.

### Repositories

- `CarRepository`: This repository handles database operations for cars.

- `QuestionRepository`: This repository handles database operations for questions.

- `ExpertiseSessionRepository`: This repository handles database operations for expertise sessions.

### Advice

- `BaseControllerAdvice`: This advice handles exceptions globally. It has methods to handle `MethodArgumentNotValidException`, `ConstraintViolationException`, `CarNotFoundException`, `ExpertiseSessionNotFoundException`, `QuestionNotFoundException`, and `InvalidExpertiseResponseException`.


### CLI

- `DataInitializer`: This command line runner initializes the database with some data on application startup.

For more details, please refer to the source code.

### TEST
- To test the POST `/api/v1/expertise/response endpoint`, you can use the following JSON body:
```sh
{
  "carId": 1,
  "answers": [
    {
      "questionId": 1,
      "answer": "Yes",
      "photoUrl": "https://example.com/photo1.jpg"
    },
    {
      "questionId": 2,
      "answer": "No",
      "photoUrl": "https://example.com/photo2.jpg"
    }
  ]
}
```
The GET `/api/v1/expertise/questions/{carId}` endpoint doesn't require a JSON body. 
You just need to replace `{carId}` with the actual ID of the car. 
For example, you can use `/api/v1/expertise/questions/1` to get all questions for the car with ID 1.

- Screenshots:
  - POST:
![Alt text](/2.png)
![Alt text](/1.png)
  - GET:
  ![Alt text](/3.png)

