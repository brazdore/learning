package com.example.playlistservice.hystrix;

import com.example.playlistservice.entities.Playlist;
import com.example.playlistservice.services.PlaylistService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.Optional;

public class HystrixPlaylistFindByIdCommand extends HystrixCommand<Optional<Playlist>> {

    private final PlaylistService playlistService;
    private final long id;

    public HystrixPlaylistFindByIdCommand(PlaylistService playlistService, long id) {
        super(HystrixCommandGroupKey.Factory.asKey("playlist"));
        this.playlistService = playlistService;
        this.id = id;
    }

    @Override
    protected Optional<Playlist> run() throws Exception {
        return playlistService.findById(id);
    }

    @Override
    protected Optional<Playlist> getFallback() {
        return Optional.of(new Playlist());
    }
}
