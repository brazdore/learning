package com.brazdore.services;

import com.brazdore.utils.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private final FileReaderService fileReaderService;
    private final FileWriterService fileWriterService;

    public FileService(FileReaderService fileReaderService, FileWriterService fileWriterService) {
        this.fileReaderService = fileReaderService;
        this.fileWriterService = fileWriterService;
    }

    public Boolean readAndWrite(String sourcePath, String sourceFilename) {
        var fileLength = new File(sourcePath).length();
        var fileSizeLimit = Long.parseLong(System.getProperty("sizeLimit"));

        if (fileSizeLimit >= fileLength) {
            var finalOutputPath = FileUtility.getFinalOutputPath(System.getProperty("outputPath"), sourceFilename);

            fileReaderService.readFile(sourcePath);
            fileWriterService.writeFile(finalOutputPath);
            return true;
        } else {
            LOGGER.warn("File: Ignored");
            LOGGER.info("File Size: {}", fileLength);
            LOGGER.info("File Size Limit: {}", fileSizeLimit);
            return false;
        }
    }
}
