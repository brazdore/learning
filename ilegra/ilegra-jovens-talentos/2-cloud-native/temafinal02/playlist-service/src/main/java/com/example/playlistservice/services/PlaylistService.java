package com.example.playlistservice.services;

import com.example.playlistservice.entities.Playlist;
import com.example.playlistservice.repositories.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> findById(long id) {
        return playlistRepository.findById(id);
    }
}
