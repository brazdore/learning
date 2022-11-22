package com.brazdore.test.services;

import com.brazdore.configs.AppConfig;
import com.brazdore.services.FileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@SpringJUnitConfig(AppConfig.class)
public class FileServiceTest {

    private static File file = null;

    static {
        try {
            file = File.createTempFile("test", ".dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private FileService fileService;

    @AfterEach
    void delete() {
        if (Objects.nonNull(file)) {
            file.deleteOnExit();
        }
    }

    @Test
    void shouldReadAndWrite() {
        if (Objects.nonNull(file)) {
            System.setProperty("outputPath", file.getAbsolutePath());
            System.setProperty("sizeLimit", "524288000"); // 500MB
            Assertions.assertTrue(fileService.readAndWrite(file.getAbsolutePath(), file.getName()));

            File newFile = new File(file.getAbsolutePath() + file.getName().replace(".dat", ".done.dat"));
            newFile.deleteOnExit();
        } else throw new NullPointerException("[FILE] is null.");
    }
}
