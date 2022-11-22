package com.brazdore.dsmovie.apis;

import com.brazdore.dsmovie.dtos.MovieDTO;
import com.brazdore.dsmovie.dtos.ScoreDTO;
import com.brazdore.dsmovie.services.ScoreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/scores")
public class ScoreAPI {

    private final ScoreService service;

    public ScoreAPI(ScoreService service) {
        this.service = service;
    }

    @PutMapping
    public MovieDTO saveScore(@RequestBody ScoreDTO scoreDTO) throws Exception {
        return service.saveScore(scoreDTO);
    }
}
