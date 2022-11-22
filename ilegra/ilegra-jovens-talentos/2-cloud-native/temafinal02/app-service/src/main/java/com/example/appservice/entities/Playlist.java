package com.example.appservice.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Playlist implements Serializable {

    private Long id;
    private String title;
    private List<Long> songsId;

    public Playlist() {
    }

    public Playlist(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Playlist(Long id, String title, List<Long> songList) {
        this.id = id;
        this.title = title;
        this.songsId = songList;
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

    public List<Long> getSongsId() {
        return songsId;
    }

    public void setSongsId(List<Long> songList) {
        this.songsId = songList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist song = (Playlist) o;
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
                ", \"songsId\":" + songsId + "}";
    }
}
