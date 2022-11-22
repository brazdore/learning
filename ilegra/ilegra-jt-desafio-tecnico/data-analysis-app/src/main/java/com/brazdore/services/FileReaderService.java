package com.brazdore.services;

import com.brazdore.exceptions.FileReaderException;
import com.brazdore.services.strategies.DataServiceStrategy;
import com.brazdore.utils.DeserializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class FileReaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReaderService.class);

    private final DataServiceStrategy dataServiceStrategy;

    public FileReaderService(DataServiceStrategy dataServiceStrategy) {
        this.dataServiceStrategy = dataServiceStrategy;
    }

    public Boolean readFile(String sourcePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(sourcePath))) {
            line = br.readLine();

            while (line != null) {
                if (!line.isBlank()) {
                    var entriesList = DeserializationService.getEntriesFromLine(line);

                    entriesList.stream()
                            .forEach(entry -> {
                                var dataArray = DeserializationService.getArrayFromEntry(entry);
                                dataServiceStrategy.save(dataArray);
                            });
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new FileReaderException(e.getMessage());
        }
        LOGGER.info("File: Read");
        return true;
    }
}
