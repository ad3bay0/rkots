# rKots
## stock trading playground

## Running the application

- Package
`$ ./mvnw clean package -DskipTests=true`

- Extract libraries from `fat jar`
`$ mkdir -p target/dependency && (cd target/dependency; unzip ../*.jar)`

- Build and run docker container
`$ docker-compose up --build`

##  Verify appplication running
> check http://localhost:9000 on browser