package com.grpcflix.movie.service;

import com.ferroblesh.grpcflix.movie.MovieDto;
import com.ferroblesh.grpcflix.movie.MovieSearchRequest;
import com.ferroblesh.grpcflix.movie.MovieSearchResponse;
import com.ferroblesh.grpcflix.movie.MovieServiceGrpc;
import com.grpcflix.movie.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        List<MovieDto> movieDtoList = this.movieRepository.getMovieByGenreOrderByYearDesc(request.getGenre().toString())
                .stream()
                .map(movie -> MovieDto.newBuilder()
                        .setTitle(movie.getTitle())
                        .setYear(movie.getYear())
                        .setRating(movie.getRating())
                        .build()
                ).collect(Collectors.toList());
        responseObserver.onNext(MovieSearchResponse.newBuilder().addAllMovies(movieDtoList).build());
        responseObserver.onCompleted();
    }
}
