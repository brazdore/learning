package com.example.appservice.controllers;

import com.example.appservice.entities.PlaylistEnhanced;
import com.example.appservice.entities.Song;
import com.example.appservice.services.AppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/app")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping(value = "/songs")
    public ResponseEntity<List<Song>> findAllSongs() {
        return ResponseEntity.ok().body(appService.findAllSongs());
    }

    @GetMapping(value = "/songs/{id}")
    public ResponseEntity<Optional<Song>> findSongById(@PathVariable String id) {
        return ResponseEntity.ok().body(appService.findSongById(id));
    }

    @GetMapping(value = "/playlists")
    public ResponseEntity<List<PlaylistEnhanced>> findAllPlaylists() {
        return ResponseEntity.ok().body(appService.findAllPlaylists());
    }

    @GetMapping(value = "/playlists/{id}")
    public ResponseEntity<Optional<PlaylistEnhanced>> findPlaylistById(@PathVariable String id) {
        return ResponseEntity.ok().body(appService.findPlaylistById(id));
    }
}
