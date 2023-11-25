# base image
FROM maven:3.8.4-openjdk-17

# Copy project files into the Docker image
COPY . /paradisegame

# Set the working directory inside the image
WORKDIR /paradisegame

# Compile and package the application
RUN mvn clean install

# Run the application
CMD ["mvn", "exec:java"]
