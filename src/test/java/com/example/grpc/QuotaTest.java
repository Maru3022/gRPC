package com.example.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = {
        "grpc.server.port=-1", // Случайный порт для исключения конфликтов
        "grpc.client.testClient.address=in-process:test" // Локальный канал связи
})
@DirtiesContext
public class QuotaTest {

    @GrpcClient("testClient")
    private QuotaServiceGrpc.QuotaServiceBlockingStub quotaStub;

    @Test
    void testGrpcInteraction() {
        // Создаем запрос, используя сгенерированный из .proto код
        AccessRequest request = AccessRequest.newBuilder()
                .setUserId("user-1")
                .build();

        // Выполняем gRPC вызов
        AccessResponse response = quotaStub.checkAccess(request);

        // Проверяем результат
        assertNotNull(response);
    }
}