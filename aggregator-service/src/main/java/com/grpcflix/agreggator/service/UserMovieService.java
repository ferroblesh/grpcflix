package com.grpcflix.agreggator.service;

import com.ferroblesh.grpcflix.common.Genre;
import com.ferroblesh.grpcflix.movie.MovieSearchRequest;
import com.ferroblesh.grpcflix.movie.MovieSearchResponse;
import com.ferroblesh.grpcflix.movie.MovieServiceGrpc;
import com.ferroblesh.grpcflix.user.UserGenreUpdateRequest;
import com.ferroblesh.grpcflix.user.UserResponse;
import com.ferroblesh.grpcflix.user.UserSearchRequest;
import com.ferroblesh.grpcflix.user.UserServiceGrpc;
import com.grpcflix.agreggator.dto.RecommendedMovie;
import com.grpcflix.agreggator.dto.UserGenre;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieStub;

    public List<RecommendedMovie> getUserMovieSuggestions(String loginId) {
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder().setLoginId(loginId).build();
        UserResponse userResponse = this.userStub.getUserGenre(userSearchRequest);
        MovieSearchResponse movieSearchResponse = this.movieStub.getMovies(MovieSearchRequest.newBuilder().setGenre(userResponse.getGenre()).build());
        return movieSearchResponse.getMoviesList().stream()
                .map(movie -> new RecommendedMovie(movie.getTitle(),movie.getYear(),movie.getYear()))
                .collect(Collectors.toList());
    }

    public void setUserGenre(UserGenre userGenre) {
        UserResponse userResponse = this.userStub.updateUserGenre(UserGenreUpdateRequest.newBuilder().setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase()))
                .setLoginId(userGenre.getLoginId()).build());
    }
}
