package com.example.songservice.hystrix;

import com.example.songservice.entities.Song;
import com.example.songservice.services.SongService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.Optional;

public class HystrixSongFindByIdCommand extends HystrixCommand<Optional<Song>> {

    private final SongService songService;
    private final long id;

    public HystrixSongFindByIdCommand(SongService songService, long id) {
        super(HystrixCommandGroupKey.Factory.asKey("song"));
        this.songService = songService;
        this.id = id;
    }

    @Override
    protected Optional<Song> run() {
        return songService.findById(id);
    }

    @Override
    protected Optional<Song> getFallback() {
        return Optional.of(new Song());
    }
}
