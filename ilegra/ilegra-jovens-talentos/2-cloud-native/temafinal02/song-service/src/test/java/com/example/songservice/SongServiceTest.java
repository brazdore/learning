package com.example.songservice;

import com.example.songservice.entities.Song;
import com.example.songservice.services.SongService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class SongServiceTest {

    private final Song sparks = new Song(1L, "Sparks");
    private final Song hinotori = new Song(2L, "Hinotori");

    @MockBean
    private SongService songService;

    @Test
    public void shouldFindAllSongs() {
        Mockito.when(songService.findAll()).thenReturn(List.of(sparks, hinotori));
        var songList = songService.findAll();
        Mockito.verify(songService).findAll();
        Assertions.assertEquals(List.of(sparks, hinotori), songList);
    }

    @Test
    public void shouldFindSongById() {
        Mockito.when(songService.findById(1L)).thenReturn(Optional.of(sparks));
        var song = songService.findById(1L);
        Mockito.verify(songService).findById(1L);
        Assertions.assertEquals(sparks, song.get());
    }

    @Test
    public void shouldExistById() {
        Mockito.when(songService.existsById(1L)).thenReturn(true);
        var songExists = songService.existsById(1L);
        Mockito.verify(songService).existsById(1L);
        Assertions.assertTrue(songExists);
    }

    @Test
    public void shouldFindSongListById() {
        Mockito.when(songService.findListById(List.of(1L, 2L))).thenReturn(List.of(sparks, hinotori));
        var songList = songService.findListById(List.of(1L, 2L));
        Mockito.verify(songService).findListById(List.of(1L, 2L));
        Assertions.assertEquals(List.of(sparks, hinotori), songList);
    }
}
