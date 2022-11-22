package com.brazdore.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public abstract class FileUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtility.class);

    public static void makeDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
            LOGGER.info("Directory was created: {}", path);
        } else LOGGER.info("Directory already exists: {}", path);
    }

    public static boolean isDatFile(String filename) {
        return filename.endsWith(".dat");
    }

    public static String getFinalOutputPath(String systemOutput, String filename) {
        var outputFilename = filename.toLowerCase().replace(".dat", ".done.dat");
        return systemOutput + outputFilename;
    }
}
