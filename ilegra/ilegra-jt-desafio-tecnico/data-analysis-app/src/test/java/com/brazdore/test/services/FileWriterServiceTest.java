package com.brazdore.test.services;

import com.brazdore.configs.AppConfig;
import com.brazdore.exceptions.FileWriterException;
import com.brazdore.services.FileWriterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@SpringJUnitConfig(AppConfig.class)
public class FileWriterServiceTest {

    private static File file = null;

    static {
        try {
            file = File.createTempFile("test", ".dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private FileWriterService fileWriterService;

    @AfterEach
    void delete() {
        if (Objects.nonNull(file)) {
            file.deleteOnExit();
        }
    }

    @Test
    void shouldWriteFile() {
        if (Objects.nonNull(file)) {
            Assertions.assertTrue(fileWriterService.writeFile(file.getAbsolutePath()));
        } else throw new NullPointerException("[FILE] is null.");
    }

    @Test
    void shouldThrowWhenInvalidPath() {
        Assertions.assertThrows(FileWriterException.class, () ->
                fileWriterService.writeFile(""), "[FileWriterException] Should throw when an invalid path is used.");
    }
}
