package com.example.appservice.hystrix;

import com.example.appservice.entities.Playlist;
import com.example.appservice.interfaces.IPlaylist;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.Optional;

public class HystrixAppFindPlaylistByIdCommand extends HystrixCommand<Optional<Playlist>> {

    private final IPlaylist iPlaylist;
    private final String id;

    public HystrixAppFindPlaylistByIdCommand(IPlaylist iPlaylist, String id) {
        super(HystrixCommandGroupKey.Factory.asKey("app"));
        this.iPlaylist = iPlaylist;
        this.id = id;
    }

    @Override
    protected Optional<Playlist> run() {
        return iPlaylist.findById(id);
    }

    @Override
    protected Optional<Playlist> getFallback() {
        return Optional.of(new Playlist());
    }
}
