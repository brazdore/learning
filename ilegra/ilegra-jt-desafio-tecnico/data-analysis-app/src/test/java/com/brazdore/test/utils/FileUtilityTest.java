package com.brazdore.test.utils;

import com.brazdore.utils.FileUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class FileUtilityTest {

    @Test
    void shouldCreateDirectory() {
        FileUtility.makeDirectory("path");
        File file = new File("path");
        Assertions.assertTrue(file.exists());
        if (file.exists()) {
            file.deleteOnExit();
        }
    }

    @Test
    void shouldAssertIsDatFile() {
        Assertions.assertTrue(FileUtility.isDatFile("test.dat"));
        Assertions.assertFalse(FileUtility.isDatFile("test.txt"));
    }

    @Test
    void shouldGetFinalOutputPath() {
        Assertions.assertEquals("data/test.done.dat", FileUtility.getFinalOutputPath("data/", "test.dat"));
    }
}
