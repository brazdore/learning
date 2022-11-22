package com.brazdore.services;

import com.brazdore.exceptions.DirectoryWatcherException;
import com.brazdore.utils.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@Service
public abstract class DirectoryWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryWatcher.class);
    private static final String SLASH_TYPE = System.getProperty("slashType");
    private static final String INPUT_PATH = System.getProperty("inputPath");

    public static void watch(FileService fileService) {
        try (WatchService service = FileSystems.getDefault().newWatchService()) {
            Map<WatchKey, Path> keyMap = new HashMap<>();
            Path path = Paths.get(INPUT_PATH);
            keyMap.put(path.register(service, StandardWatchEventKinds.ENTRY_CREATE), path);

            WatchKey watchKey;

            do {
                watchKey = service.take();
                Path eventDir = keyMap.get(watchKey);

                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path eventPath = (Path) event.context();

                    var isDatFile = FileUtility.isDatFile(eventPath.getFileName().toString());

                    if (isDatFile) {
                        LOGGER.info("File: Detected");
                        LOGGER.info("Entry Type: {}", kind.name());
                        LOGGER.info("Event Directory: {}", eventDir.toString());
                        LOGGER.info("Event Path: {}", eventPath);
                        LOGGER.info("Input Path: {}", eventDir + SLASH_TYPE + eventPath);
                        fileService.readAndWrite(eventDir + SLASH_TYPE + eventPath, eventPath.getFileName().toString());
                    }
                }
            } while (watchKey.reset());
        } catch (IOException | InterruptedException e) {
            throw new DirectoryWatcherException(e.getMessage());
        }
    }
}
