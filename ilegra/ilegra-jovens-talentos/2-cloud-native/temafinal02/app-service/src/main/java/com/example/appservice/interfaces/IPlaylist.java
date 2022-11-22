package com.example.appservice.interfaces;

import com.example.appservice.entities.Playlist;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface IPlaylist {

    @RequestLine("GET /playlists/")
    List<Playlist> findAll();

    @RequestLine("GET /playlists/{id}")
    Optional<Playlist> findById(@Param("id") String id);
}
