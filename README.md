# gRPC Quota Service

Простой пример gRPC сервиса на Java с использованием Spring Boot.  
Сервис реализует проверку доступа пользователя через RPC метод `CheckAccess`.

Проект демонстрирует:
- создание gRPC сервиса
- использование Protocol Buffers
- интеграцию gRPC со Spring Boot
- unit тестирование gRPC сервиса
- генерацию Java кода из `.proto` файла

--------------------------------------------------

ТЕХНОЛОГИИ

Java 17  
Spring Boot 3  
gRPC  
Protocol Buffers  
Gradle  
JUnit 5

--------------------------------------------------

СТРУКТУРА ПРОЕКТА

project

src  
├─ main  
│   ├─ java/com/example/grpc  
│   │   └─ QuotaServiceImpl.java  
│   │  
│   └─ proto  
│       └─ quota.proto  
│  
└─ test  
└─ java/com/example/grpc  
└─ QuotaServiceTest.java

build.gradle  
README.md

--------------------------------------------------

gRPC API

Service

service QuotaService {
rpc CheckAccess(AccessRequest) returns (AccessResponse);
}

Request

message AccessRequest {
string user_id = 1;
}

Response

message AccessResponse {
bool allowed = 1;
}

--------------------------------------------------

РЕАЛИЗАЦИЯ СЕРВИСА

Сервис реализует метод `checkAccess`, который принимает `AccessRequest`
и возвращает `AccessResponse`.

Текущая реализация демонстрационная и всегда возвращает разрешение доступа.

@Override
public void checkAccess(AccessRequest request, StreamObserver<AccessResponse> responseObserver) {

    AccessResponse response = AccessResponse.newBuilder()
            .setAllowed(true)
            .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
}

--------------------------------------------------

ГЕНЕРАЦИЯ КОДА

Java код автоматически генерируется из `.proto` файла с помощью protobuf Gradle plugin.

Сгенерированные директории:

build/generated/source/proto/main/java  
build/generated/source/proto/main/grpc

--------------------------------------------------

СБОРКА ПРОЕКТА

./gradlew build

--------------------------------------------------

ЗАПУСК ТЕСТОВ

./gradlew test

--------------------------------------------------

UNIT ТЕСТ

Для тестирования используется InProcess gRPC server.  
Это позволяет запускать сервер и клиент внутри одного процесса без сетевого соединения.

Пример тестового запроса:

AccessRequest request = AccessRequest.newBuilder()
.setUserId("user-1")
.build();

AccessResponse response = blockingStub.checkAccess(request);

Проверка результата:

assertTrue(response.getAllowed());

--------------------------------------------------

ЗАВИСИМОСТИ

Основные библиотеки проекта:

grpc-server-spring-boot-starter  
grpc-client-spring-boot-starter  
grpc-protobuf  
grpc-stub  
protobuf-java  
grpc-testing  
grpc-inprocess  
spring-boot-starter-test

--------------------------------------------------

НАЗНАЧЕНИЕ ПРОЕКТА

Проект предназначен для:
- изучения gRPC на Java
- демонстрации интеграции gRPC и Spring Boot
- примера unit тестирования gRPC сервисов