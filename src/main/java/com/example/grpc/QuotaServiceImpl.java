package com.example.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class QuotaServiceImpl extends QuotaServiceGrpc.QuotaServiceImplBase {

    @Override
    public void checkAccess(AccessRequest request, StreamObserver<AccessResponse> responseObserver) {
        AccessResponse response = AccessResponse.newBuilder()
                .setAllowed(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
