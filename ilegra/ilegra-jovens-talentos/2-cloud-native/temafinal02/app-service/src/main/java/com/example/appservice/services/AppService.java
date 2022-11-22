package com.example.appservice.services;

import com.example.appservice.entities.PlaylistEnhanced;
import com.example.appservice.entities.Song;
import com.example.appservice.ribbon.PlaylistRibbon;
import com.example.appservice.ribbon.SongRibbon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AppService {

    private final SongRibbon songRibbon;
    private final PlaylistRibbon playlistRibbon;

    public AppService(SongRibbon songRibbon, PlaylistRibbon playlistRibbon) {
        this.songRibbon = songRibbon;
        this.playlistRibbon = playlistRibbon;
    }

    public Optional<Song> findSongById(String id) {
        return songRibbon.findSongById(id);
    }

    public List<Song> findAllSongs() {
        return songRibbon.findAllSongs();
    }

    public List<PlaylistEnhanced> findAllPlaylists() {
        List<PlaylistEnhanced> playlistSongs = new ArrayList<>();
        var playlists = playlistRibbon.findAllPlaylists();
        playlists.forEach(p -> playlistSongs.add(new PlaylistEnhanced(p.getId(), p.getTitle(), findSongListById(p.getSongsId()))));
        return playlistSongs;
    }

    public Optional<PlaylistEnhanced> findPlaylistById(String id) {
        var playlist = playlistRibbon.findPlaylistById(id);
        return playlist.map(value -> new PlaylistEnhanced(value.getId(), value.getTitle(), findSongListById(value.getSongsId())));
    }

    private List<Song> findSongListById(List<Long> idList) {
        List<String> stringList = new ArrayList<>();
        StringBuilder stringIdList = new StringBuilder();

        if (Objects.nonNull(idList)) {
            idList.forEach(l -> stringList.add(l.toString()));
            for (String s : stringList) {
                stringIdList.append(s);
                stringIdList.append(",");
            }
            stringIdList.deleteCharAt(stringIdList.length() - 1);
        }
        return songRibbon.findSongListById(stringIdList.toString());
    }
}
