# GoldInvesting Application Deployment

Welcome to the deployment guide for the GoldInvesting application. This guide covers the necessary steps to build and run the application using Docker and Docker Compose. The application is split into two main parts: a Java Spring Boot backend and a React.js frontend.

## Prerequisites

Before proceeding, ensure you have the following installed on your system:

- Docker
- Docker Compose

You can download Docker, which includes Docker Compose, from [https://www.docker.com/get-started](https://www.docker.com/get-started).

## Project Structure

The application is organized into two directories:

- **Backend:** Located in `backend/goldinvesting`, it's a Spring Boot application running on Java 21.
- **Frontend:** Located in `frontend/goldinvesting`, it's a React.js application.

## Building the Docker Images

We use Docker to containerize both the frontend and backend parts of the application. The `Dockerfile` for each part specifies how to build the images.

### Backend Dockerfile

The backend `Dockerfile` is located in the `backend/goldinvesting` directory. It uses `openjdk:21-slim` as the base image to build the Spring Boot application.

### Frontend Dockerfile

The frontend `Dockerfile` is located in the `frontend/goldinvesting` directory. It uses `node:latest` as the base image to build the React.js application and `nginx:alpine` to serve the static files.

## Using Docker Compose

To simplify the building and running of our multi-container application, we use Docker Compose. The `docker-compose.yml` file is located at the root of the project.

### docker-compose.yml Overview

The Docker Compose file defines two services:

- `backend`: Builds and runs the Spring Boot application.
- `frontend`: Builds and runs the React.js application, served via Nginx.

Networks and volumes can be customized as needed. Refer to the Docker Compose file for detailed configurations.

### Running the Application with Docker Compose

To build and run the application:

1. Open a terminal and navigate to the root directory of the project.
2. Run `docker-compose build` to build the images for both the frontend and backend.
3. Run `docker-compose up` to start the application. Use `-d` to run in detached mode.

The frontend will be accessible at `http://localhost:5000`, and the backend will be accessible at `http://localhost:8080`.

### Stopping the Application

To stop and remove the containers, networks, and all the resources created by Docker Compose, run `docker-compose down` from the root directory of the project.

## Additional Notes

- To rebuild the application after making changes, repeat the build and up steps.
- Environment variables and other configurations can be adjusted in the Docker Compose file or the respective Dockerfiles.
