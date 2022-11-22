package com.example.playlistservice.controllers;

import com.example.playlistservice.entities.Playlist;
import com.example.playlistservice.exceptions.InvalidPlaylistIdFormatException;
import com.example.playlistservice.hystrix.HystrixPlaylistFindAllCommand;
import com.example.playlistservice.hystrix.HystrixPlaylistFindByIdCommand;
import com.example.playlistservice.services.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Playlist>> findAll() {
        return ResponseEntity.ok().body(new HystrixPlaylistFindAllCommand(playlistService).execute());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Playlist>> findById(@PathVariable String id) throws Exception {
        try {
            var longId = Long.parseLong(id);
            return ResponseEntity.ok().body(new HystrixPlaylistFindByIdCommand(playlistService, longId).execute());
        } catch (NumberFormatException e) {
            throw new InvalidPlaylistIdFormatException(id);
        }
    }
}
