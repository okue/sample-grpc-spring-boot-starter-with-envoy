# Sample project for grpc-spring-boot-starter and envoy json transcoder

## Build

```
$ ./gradlew mySetUpProto
$ ./gradlew :app:api:bootRun
$ ./gradlew :app:internal-api:bootRun

$ docker build -t envoy/json-transcoder:latest .
$ docker run -it --rm -p 8081:8081 envoy/json-transcoder

$ curl -v 0.0.0.0:8081/v1/api/reserve/{id}
$ curl -v -XPOST 0.0.0.0:8081/v1/api/hello -d '{"message": "Good morning", "from": {"id": "tanaka"}}'
```
