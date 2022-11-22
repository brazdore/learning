package com.example.songservice.hystrix;

import com.example.songservice.entities.Song;
import com.example.songservice.services.SongService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

public class HystrixSongFindAllCommand extends HystrixCommand<List<Song>> {

    private final SongService songService;

    public HystrixSongFindAllCommand(SongService songService) {
        super(HystrixCommandGroupKey.Factory.asKey("song"));
        this.songService = songService;
    }

    @Override
    protected List<Song> run() throws Exception {
        return songService.findAll();
    }

    @Override
    protected List<Song> getFallback() {
        return List.of();
    }
}
