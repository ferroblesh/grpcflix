package com.grpcflix.agreggator.controller;

import com.grpcflix.agreggator.dto.RecommendedMovie;
import com.grpcflix.agreggator.dto.UserGenre;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AggregatorController {

    @GetMapping("/user/{loginId}")
    public List<RecommendedMovie> getMovies(@PathVariable String loginId) {

    }

    @PutMapping("/user")
    public void setUserGenre(@RequestBody UserGenre userGenre) {

    }
}
