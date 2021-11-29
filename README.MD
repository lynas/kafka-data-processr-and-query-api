# Kafka stream processor and query API

- There are two projects
- One for receiving stream data and store it in the database [data-processor]
- One for REST API that query database and return result [rest-api]

## How to run the projects

## data-processor

- Add kafka and db config in the following file
- `data-processor/src/main/resources/application.properties`
- Create a table with following query
```sql
create table event
(
    id          varchar(100) not nullprimary key,
    event_id    varchar(100) null,
    type        varchar(100) null,
    name        varchar(100) null,
    cluster_id  varchar(100) null,
    timestamp   timestamp    null,
    value       double       null,
    initialized varchar(100) null
);
```
- cd in `data-processor` project from terminal and run following command to run the project
- `./gradlew bootRun`
- You can also create docker image and run docker container with following commands
```
./gradlew bootJar

docker build --build-arg JAR_FILE=build/libs/\*.jar -t relay42/data-processor .

docker run -it relay42/data-processor
```

## rest-api

- Add db config in the following file
- `rest-api/src/main/resources/application.properties`
- cd in `rest-api` project from terminal and run following command to run the project
- `./gradlew bootRun`


- You can also create docker image and run docker container with following commands
```
./gradlew bootJar

docker build --build-arg JAR_FILE=build/libs/\*.jar -t relay42/rest-api .

docker run -it relay42/rest-api
```
- Sample REST api call

```

curl --location --request GET 'http://localhost:8811/events/operation/min/fromTime/1637840755629/toTime/1637840775650' \
--header 'Authorization: Basic dXNlcjp1c2Vy'

curl --location --request GET 'http://localhost:8811/events/operation/min/fromTime/1637840755629/toTime/1637840775650?eventType=HUMIDITY&clusterId=1' \
--header 'Authorization: Basic dXNlcjp1c2Vy'

```

- Available operation `min, max, median, average`
- `fromTime/toTime` is time in `miliseconds`

- Sample response
```
GET http://localhost:8811/events/operation/min/fromTime/1637840755629/toTime/1637840775650?eventType=HUMIDITY&clusterId=1

HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 17

59.98576375470387

Response code: 200 (OK); Time: 728ms; Content length: 17 bytes
```

## Limitation
- A document databse or time series database may perform better if input dataset is very big
- Can create docker image with gradle



