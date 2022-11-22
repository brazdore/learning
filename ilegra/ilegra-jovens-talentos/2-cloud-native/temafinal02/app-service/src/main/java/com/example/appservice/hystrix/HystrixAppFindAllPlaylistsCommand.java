package com.example.appservice.hystrix;

import com.example.appservice.entities.Playlist;
import com.example.appservice.interfaces.IPlaylist;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

public class HystrixAppFindAllPlaylistsCommand extends HystrixCommand<List<Playlist>> {

    private final IPlaylist iPlaylist;

    public HystrixAppFindAllPlaylistsCommand(IPlaylist iPlaylist) {
        super(HystrixCommandGroupKey.Factory.asKey("app"));
        this.iPlaylist = iPlaylist;
    }

    @Override
    protected List<Playlist> run() throws Exception {
        return iPlaylist.findAll();
    }

    @Override
    protected List<Playlist> getFallback() {
        return List.of();
    }
}
