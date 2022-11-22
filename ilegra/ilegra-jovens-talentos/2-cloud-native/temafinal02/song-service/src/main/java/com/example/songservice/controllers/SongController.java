package com.example.songservice.controllers;

import com.example.songservice.entities.Song;
import com.example.songservice.exceptions.InvalidSongIdFormatException;
import com.example.songservice.exceptions.InvalidSongIdListFormatException;
import com.example.songservice.hystrix.HystrixSongFindAllCommand;
import com.example.songservice.hystrix.HystrixSongFindByIdCommand;
import com.example.songservice.hystrix.HystrixSongFindListByIdCommand;
import com.example.songservice.services.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Song>> findAll() {
        return ResponseEntity.ok().body(new HystrixSongFindAllCommand(songService).execute());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Song>> findById(@PathVariable String id) throws Exception {
        try {
            var longId = Long.parseLong(id);
            return ResponseEntity.ok().body(new HystrixSongFindByIdCommand(songService, longId).execute());
        } catch (NumberFormatException e) {
            throw new InvalidSongIdFormatException(id);
        }
    }

    @GetMapping(value = "/list/{idList}")
    public ResponseEntity<List<Song>> findListById(@PathVariable String idList) throws Exception {
        try {
            List<Long> longIdList = new ArrayList<>();
            List<String> stringList = Arrays.asList(idList.split(","));
            stringList.forEach(x -> longIdList.add(Long.parseLong(x)));
            return ResponseEntity.ok().body(new HystrixSongFindListByIdCommand(songService, longIdList).execute());
        } catch (NumberFormatException e) {
            throw new InvalidSongIdListFormatException(idList);
        }
    }
}
