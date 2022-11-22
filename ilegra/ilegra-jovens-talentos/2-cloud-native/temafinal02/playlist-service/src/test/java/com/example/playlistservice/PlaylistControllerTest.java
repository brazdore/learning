package com.example.playlistservice;

import com.example.playlistservice.controllers.PlaylistController;
import com.example.playlistservice.entities.Playlist;
import com.example.playlistservice.exceptions.InvalidPlaylistIdFormatException;
import com.example.playlistservice.services.PlaylistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaylistController.class)
public class PlaylistControllerTest {

    private final Playlist holosongs = new Playlist(1L, "Holosongs", List.of(1L, 2L, 3L));
    private final Playlist kiara = new Playlist(2L, "Kiara-Playlist", List.of(1L, 3L));
    private final List<Playlist> playlistList = List.of(holosongs, kiara);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlaylistService playlistService;

    @Test
    public void shouldFindAllPlaylistsController() throws Exception {
        Mockito.when(playlistService.findAll()).thenReturn(playlistList);

        String result = mvc.perform(MockMvcRequestBuilders.get("/playlists/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(playlistService).findAll();

        var playlistString = playlistList.toString().replaceAll(" ", "");

        Assertions.assertEquals(playlistString, result);
    }

    @Test
    public void shouldFindPlaylistByIdController() throws Exception {
        Mockito.when(playlistService.findById(1L)).thenReturn(Optional.of(holosongs));

        String result = mvc.perform(MockMvcRequestBuilders.get("/playlists/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(playlistService).findById(1L);

        var playlistString = holosongs.toString().replaceAll(" ", "");

        Assertions.assertEquals(playlistString, result);
    }

    @Test
    public void shouldThrowAtInvalidPlaylistIdController() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/playlists/a"))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof InvalidPlaylistIdFormatException))
                .andExpect(x -> Assertions.assertEquals("Invalid ID. [ID] must be a valid number. (a) is not valid."
                        , Objects.requireNonNull(x.getResolvedException()).getLocalizedMessage()));
    }
}
