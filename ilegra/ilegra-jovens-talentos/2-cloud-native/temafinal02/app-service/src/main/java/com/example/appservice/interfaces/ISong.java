package com.example.appservice.interfaces;

import com.example.appservice.entities.Song;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ISong {

    @RequestLine("GET /songs/")
    List<Song> findAll();

    @RequestLine("GET /songs/{id}")
    Optional<Song> findById(@Param("id") String id);

    @RequestLine("GET /songs/list/{idList}")
    List<Song> findSongListById(@Param("idList") String idList);
}
