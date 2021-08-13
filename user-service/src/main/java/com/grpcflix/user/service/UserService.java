package com.grpcflix.user.service;

import com.ferroblesh.grpcflix.common.Genre;
import com.ferroblesh.grpcflix.user.UserGenreUpdateRequest;
import com.ferroblesh.grpcflix.user.UserResponse;
import com.ferroblesh.grpcflix.user.UserSearchRequest;
import com.ferroblesh.grpcflix.user.UserServiceGrpc;
import com.grpcflix.user.entity.User;
import com.grpcflix.user.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserRepository userRepository;

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder userResponseBuilder = UserResponse.newBuilder();
        List<User> all = this.userRepository.findAll();
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                  userResponseBuilder.setName(user.getName())
                      .setLoginId(user.getLogin())
                      .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });
        responseObserver.onNext(userResponseBuilder.build());
        responseObserver.onCompleted();
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder userResponseBuilder = UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre().toString());
                    userResponseBuilder.setName(user.getName())
                            .setLoginId(user.getLogin())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });
        responseObserver.onNext(userResponseBuilder.build());
        responseObserver.onCompleted();
    }
}
