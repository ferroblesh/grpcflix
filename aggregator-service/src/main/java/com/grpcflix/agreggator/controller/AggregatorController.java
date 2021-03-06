package com.grpcflix.agreggator.controller;

import com.grpcflix.agreggator.dto.RecommendedMovie;
import com.grpcflix.agreggator.dto.UserGenre;
import com.grpcflix.agreggator.service.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AggregatorController {

    @Autowired
    private UserMovieService userMovieService;

    @GetMapping("/user/{loginId}")
    public List<RecommendedMovie> getMovies(@PathVariable String loginId) {
        return this.userMovieService.getUserMovieSuggestions(loginId);
    }

    @PutMapping("/user")
    public void setUserGenre(@RequestBody UserGenre userGenre) {
        this.userMovieService.setUserGenre(userGenre);
    }
}
