package com.example.playlistservice;

import com.example.playlistservice.entities.Playlist;
import com.example.playlistservice.services.PlaylistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class PlaylistServiceTest {

    private final Playlist holosongs = new Playlist(1L, "Holosongs", List.of(1L, 2L, 3L));
    private final Playlist kiara = new Playlist(2L, "Holosongs", List.of(1L, 3L));
    private final List<Playlist> playlistList = List.of(holosongs, kiara);

    @MockBean
    private PlaylistService playlistService;

    @Test
    public void shouldFindAllPlaylists() {
        Mockito.when(playlistService.findAll()).thenReturn(playlistList);
        var songList = playlistService.findAll();
        Mockito.verify(playlistService).findAll();
        Assertions.assertEquals(playlistList, songList);
    }

    @Test
    public void shouldFindSongById() {
        Mockito.when(playlistService.findById(1L)).thenReturn(Optional.of(holosongs));
        var song = playlistService.findById(1L);
        Mockito.verify(playlistService).findById(1L);
        Assertions.assertEquals(holosongs, song.get());
    }
}
