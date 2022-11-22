package com.brazdore.dsmovie.repositories;

import com.brazdore.dsmovie.entities.Score;
import com.brazdore.dsmovie.entities.ScorePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, ScorePK> {
}
