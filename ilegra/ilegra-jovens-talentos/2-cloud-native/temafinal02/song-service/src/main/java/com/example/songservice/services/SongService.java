package com.example.songservice.services;

import com.example.songservice.entities.Song;
import com.example.songservice.repositories.SongRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> findAll() {
        return songRepository.findAll();
    }

    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    public boolean existsById(long id) {
        return songRepository.existsById(id);
    }

    public List<Song> findListById(List<Long> idList) {
        List<Song> songList = new ArrayList<>();
        for (Long id : idList) {
            if (existsById(id)) {
                songList.add(findById(id).get());
            }
        }
        return songList;
    }
}
