package com.example.appservice.hystrix;

import com.example.appservice.entities.Song;
import com.example.appservice.interfaces.ISong;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

public class HystrixAppFindAllSongsCommand extends HystrixCommand<List<Song>> {

    private final ISong iSong;

    public HystrixAppFindAllSongsCommand(ISong iSong) {
        super(HystrixCommandGroupKey.Factory.asKey("app"));
        this.iSong = iSong;
    }

    @Override
    protected List<Song> run() throws Exception {
        return iSong.findAll();
    }

    @Override
    protected List<Song> getFallback() {
        return List.of();
    }
}
