package com.brazdore.dsmovie.services;

import com.brazdore.dsmovie.dtos.MovieDTO;
import com.brazdore.dsmovie.dtos.ScoreDTO;
import com.brazdore.dsmovie.entities.Movie;
import com.brazdore.dsmovie.entities.Score;
import com.brazdore.dsmovie.entities.User;
import com.brazdore.dsmovie.exceptions.ResourceNotFoundException;
import com.brazdore.dsmovie.repositories.MovieRepository;
import com.brazdore.dsmovie.repositories.ScoreRepository;
import com.brazdore.dsmovie.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ScoreService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;

    public ScoreService(MovieRepository movieRepository, UserRepository userRepository, ScoreRepository scoreRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
    }

    @Transactional
    public MovieDTO saveScore(ScoreDTO scoreDTO) throws Exception {
        Optional<Movie> optionalMovie = movieRepository.findById(scoreDTO.getMovieId());

        if (optionalMovie.isEmpty()) {
            throw new ResourceNotFoundException("Movie não foi encontrado!");
        }

        Movie movie = optionalMovie.get();

        User user = userRepository.findByEmail(scoreDTO.getEmail());

        if (user == null) {
            user = new User();
            user.setEmail(scoreDTO.getEmail());
            user = userRepository.saveAndFlush(user);
        }

        Score score = new Score();
        score.setMovie(movie);
        score.setUser(user);
        score.setValue(scoreDTO.getScore());

        scoreRepository.saveAndFlush(score);

        AtomicReference<Double> sum = new AtomicReference<>(0D);

        movie.getScores().stream()
                .parallel()
                .forEach(m -> sum.updateAndGet(v -> v + m.getValue()));

        double avg = sum.get() / movie.getScores().size();

        movie.setScore(avg);
        movie.setCount(movie.getScores().size());

        return new MovieDTO(movieRepository.save(movie));
    }
}
