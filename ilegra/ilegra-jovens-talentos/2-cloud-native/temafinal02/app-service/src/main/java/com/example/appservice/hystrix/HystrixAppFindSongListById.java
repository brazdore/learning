package com.example.appservice.hystrix;

import com.example.appservice.entities.Song;
import com.example.appservice.interfaces.ISong;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

public class HystrixAppFindSongListById extends HystrixCommand<List<Song>> {

    private final ISong iSong;
    private final String idList;

    public HystrixAppFindSongListById(ISong iSong, String id) {
        super(HystrixCommandGroupKey.Factory.asKey("app"));
        this.iSong = iSong;
        this.idList = id;
    }

    @Override
    protected List<Song> run() throws Exception {
        return iSong.findSongListById(idList);
    }

    @Override
    protected List<Song> getFallback() {
        return List.of();
    }
}
