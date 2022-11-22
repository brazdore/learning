package com.example.songservice;

import com.example.songservice.controllers.SongController;
import com.example.songservice.entities.Song;
import com.example.songservice.exceptions.InvalidSongIdFormatException;
import com.example.songservice.exceptions.InvalidSongIdListFormatException;
import com.example.songservice.services.SongService;
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

@WebMvcTest(SongController.class)
public class SongControllerTest {

    private final Song sparks = new Song(1L, "Sparks");
    private final Song hinotori = new Song(2L, "Hinotori");
    private final List<Song> songList = List.of(sparks, hinotori);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SongService songService;

    @Test
    public void shouldFindAllSongsController() throws Exception {
        Mockito.when(songService.findAll()).thenReturn(songList);

        String result = mvc.perform(MockMvcRequestBuilders.get("/songs/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(songService).findAll();
        var songString = songList.toString().replaceAll(" ", "");

        Assertions.assertEquals(songString, result);
    }

    @Test
    public void shouldFindSongByIdController() throws Exception {
        Mockito.when(songService.findById(1L)).thenReturn(Optional.of(sparks));

        String result = mvc.perform(MockMvcRequestBuilders.get("/songs/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(songService).findById(1L);

        Assertions.assertEquals(sparks.toString(), result);
    }

    @Test
    public void shouldFindSongListByIdController() throws Exception {
        Mockito.when(songService.findListById(List.of(1L, 2L))).thenReturn(songList);

        String result = mvc.perform(MockMvcRequestBuilders.get("/songs/list/1,2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(songService).findListById(List.of(1L, 2L));
        var songString = songList.toString().replaceAll(" ", "");

        Assertions.assertEquals(songString, result);
    }

    @Test
    public void shouldThrowAtInvalidSongIdController() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/songs/a"))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof InvalidSongIdFormatException))
                .andExpect(x -> Assertions.assertEquals("Invalid ID. [ID] must be a valid number. (a) is not valid."
                        , Objects.requireNonNull(x.getResolvedException()).getLocalizedMessage()));
    }

    @Test
    public void shouldThrowAtInvalidSongIdListController() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/songs/list/1,2,a"))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof InvalidSongIdListFormatException))
                .andExpect(x -> Assertions.assertEquals("Invalid ID-LIST. [ID-LIST] must be a comma-separated number list. (1,2,a) is not valid."
                        , Objects.requireNonNull(x.getResolvedException()).getLocalizedMessage()));
    }
}
