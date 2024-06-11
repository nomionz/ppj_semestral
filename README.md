# Weather Data Application

## Project Overview
This project is a Java Spring Boot application developed as a university project. The goal is to create an application for storing and displaying meteorological data.

## Technical Requirements
- **Build System:** Maven
- **Framework:** Spring Boot

## Data Model (Persistence)
- **City:** Data stored in relational database
- **Weather info for City:** Data stored in relational database

## API
The application provides a REST API for direct communication.

### REST Operations
The REST interface supports the following operations:
- Adding, editing, and deleting data for countries, cities, and measurements.
- Displaying current values and averages over the last day, week, and fortnight.

## Testing
The solution includes tests for all operations accessed via the REST API.

## Logging
- The application utilizes Logback for logging, with output directed to a file (e.g., log.out). In case of errors, the `log.out` file will be reviewed, especially if tests fail.

## Data Source
- Data can be sourced from any publicly available API, such as [OpenWeatherMap](http://www.openweathermap.com) with a free access limit of 60 calls per minute.
