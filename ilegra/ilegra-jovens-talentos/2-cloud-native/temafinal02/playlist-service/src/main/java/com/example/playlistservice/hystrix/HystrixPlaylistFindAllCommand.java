package com.example.playlistservice.hystrix;

import com.example.playlistservice.entities.Playlist;
import com.example.playlistservice.services.PlaylistService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

public class HystrixPlaylistFindAllCommand extends HystrixCommand<List<Playlist>> {

    private final PlaylistService playlistService;

    public HystrixPlaylistFindAllCommand(PlaylistService playlistService) {
        super(HystrixCommandGroupKey.Factory.asKey("playlist"));
        this.playlistService = playlistService;
    }

    @Override
    protected List<Playlist> run() {
        return playlistService.findAll();
    }

    @Override
    protected List<Playlist> getFallback() {
        return List.of();
    }
}
