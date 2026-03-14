package com.example.grpc;

import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuotaServiceTest {

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    private static final String SERVER_NAME = "in-process-server";

    @Test
    public void testCheckAccess() throws IOException {
        // Создаем сервер
        QuotaServiceImpl serviceImpl = new QuotaServiceImpl();
        grpcCleanup.register(InProcessServerBuilder
                .forName(SERVER_NAME)
                .directExecutor()
                .addService(serviceImpl)
                .build()
                .start());

        // Создаем клиент
        QuotaServiceGrpc.QuotaServiceBlockingStub blockingStub =
                QuotaServiceGrpc.newBlockingStub(
                        grpcCleanup.register(
                                InProcessChannelBuilder
                                        .forName(SERVER_NAME)
                                        .directExecutor()
                                        .build()));

        // Тест
        AccessRequest request = AccessRequest.newBuilder()
                .setUserId("user-1")
                .build();

        AccessResponse response = blockingStub.checkAccess(request);
        assertTrue(response.getAllowed());
    }
}
