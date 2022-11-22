package com.example.appservice.hystrix;

import com.example.appservice.entities.Song;
import com.example.appservice.interfaces.ISong;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.Optional;

public class HystrixAppFindSongByIdCommand extends HystrixCommand<Optional<Song>> {

    private final String id;
    private final ISong iSong;

    public HystrixAppFindSongByIdCommand(ISong isong, String id) {
        super(HystrixCommandGroupKey.Factory.asKey("app"));
        this.id = id;
        this.iSong = isong;
    }

    @Override
    protected Optional<Song> run() {
        return iSong.findById(id);
    }

    @Override
    protected Optional<Song> getFallback() {
        return Optional.of(new Song());
    }
}
