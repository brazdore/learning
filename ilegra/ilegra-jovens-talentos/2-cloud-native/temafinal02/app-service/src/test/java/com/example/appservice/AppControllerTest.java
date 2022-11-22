package com.example.appservice;

import com.example.appservice.controllers.AppController;
import com.example.appservice.entities.PlaylistEnhanced;
import com.example.appservice.entities.Song;
import com.example.appservice.services.AppService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppController.class)
public class AppControllerTest {

    private final Song sparks = new Song(1L, "Sparks");
    private final Song hinotori = new Song(2L, "Hinotori");
    private final Song infinity = new Song(3L, "Infinity");
    private final List<Song> songList = List.of(sparks, hinotori, infinity);

    private final PlaylistEnhanced holosongsEnhanced = new PlaylistEnhanced(1L, "Holosongs", songList);
    private final PlaylistEnhanced kiaraEnhanced = new PlaylistEnhanced(2L, "KFP-Vibes", List.of(sparks, hinotori));
    private final List<PlaylistEnhanced> playlistEnhancedList = List.of(holosongsEnhanced, kiaraEnhanced);

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AppService appService;

    @Test
    public void shouldFindAllSongsController() throws Exception {
        Mockito.when(appService.findAllSongs()).thenReturn(songList);

        String result = mvc.perform(MockMvcRequestBuilders.get("/app/songs/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(appService).findAllSongs();
        var songString = songList.toString().replaceAll(" ", "");

        Assertions.assertEquals(songString, result);
    }

    @Test
    public void shouldFindSongByIdController() throws Exception {
        Mockito.when(appService.findSongById("1")).thenReturn(Optional.of(sparks));

        String result = mvc.perform(MockMvcRequestBuilders.get("/app/songs/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(appService).findSongById("1");

        Assertions.assertEquals(sparks.toString(), result);
    }

    @Test
    public void shouldFindAllEnhancedPlaylistsController() throws Exception {
        Mockito.when(appService.findAllPlaylists()).thenReturn(playlistEnhancedList);

        String result = mvc.perform(MockMvcRequestBuilders.get("/app/playlists/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(appService).findAllPlaylists();

        var playlistString = playlistEnhancedList.toString().replaceAll(" ", "");

        Assertions.assertEquals(playlistString, result);
    }

    @Test
    public void shouldFindEnhancedPlaylistByIdController() throws Exception {
        Mockito.when(appService.findPlaylistById("2")).thenReturn(Optional.of(kiaraEnhanced));

        String result = mvc.perform(MockMvcRequestBuilders.get("/app/playlists/2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(appService).findPlaylistById("2");

        var playlistString = kiaraEnhanced.toString().replaceAll(" ", "");

        Assertions.assertEquals(playlistString, result);
    }
}
