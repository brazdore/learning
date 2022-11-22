package com.brazdore.test.services;

import com.brazdore.configs.AppConfig;
import com.brazdore.exceptions.DirectoryWatcherException;
import com.brazdore.services.DirectoryWatcher;
import com.brazdore.services.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(AppConfig.class)
public class DirectoryWatcherTest {

    @Autowired
    private FileService fileService;

    @Test
    void shouldThrowAtInvalidPath() {
        System.setProperty("inputPath", "invalid path");
        Assertions.assertThrows(DirectoryWatcherException.class, () ->
                DirectoryWatcher.watch(fileService), "[DirectoryWatcherException] Should throw when directory is not found.");
    }
}
