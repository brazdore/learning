package com.example.appservice.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PlaylistEnhanced implements Serializable {

    private Long id;
    private String title;
    private List<Song> songList;

    public PlaylistEnhanced() {
    }

    public PlaylistEnhanced(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public PlaylistEnhanced(Long id, String title, List<Song> songList) {
        this.id = id;
        this.title = title;
        this.songList = songList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistEnhanced song = (PlaylistEnhanced) o;
        return id.equals(song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{\"id\":" + id +
                ",\"title\":\"" + title + "\"" +
                ", \"songList\":" + songList + "}";
    }
}
