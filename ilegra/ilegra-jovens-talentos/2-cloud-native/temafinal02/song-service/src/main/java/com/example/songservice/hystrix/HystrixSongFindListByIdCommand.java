package com.example.songservice.hystrix;

import com.example.songservice.entities.Song;
import com.example.songservice.services.SongService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

public class HystrixSongFindListByIdCommand extends HystrixCommand<List<Song>> {

    private final SongService songService;
    private final List<Long> idList;

    public HystrixSongFindListByIdCommand(SongService songService, List<Long> idList) {
        super(HystrixCommandGroupKey.Factory.asKey("song"));
        this.songService = songService;
        this.idList = idList;
    }

    @Override
    protected List<Song> run() throws Exception {
        return songService.findListById(idList);
    }

    @Override
    protected List<Song> getFallback() {
        return List.of();
    }
}
